package com.example.livapp.service.implementation.pack;

import com.example.livapp.model.types.OrderNoteType;
import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.pack.PackHistory;
import com.example.livapp.model.pack.PackHolder;
import com.example.livapp.model.pack.PackProducts;
import com.example.livapp.model.product.Product;
import com.example.livapp.model.user.User;
import com.example.livapp.repository.pack.PackRepository;
import com.example.livapp.service.abstraction.pack.PackHistoryService;
import com.example.livapp.service.abstraction.pack.PackProductsService;
import com.example.livapp.service.abstraction.pack.PackService;
import com.example.livapp.service.abstraction.product.ProductService;
import com.example.livapp.service.abstraction.user.UserService;
import com.example.livapp.util.ListUtil;
import com.example.livapp.util.ObjectMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PackServiceImpl implements PackService {

    private final PackRepository packRepository;

    private final ProductService productService;

    private final PackProductsService packProductsService;


    private final PackHistoryService packHistoryService;


    private final UserService userService;

    @Autowired
    public PackServiceImpl(PackRepository packRepository, ProductService productService, PackProductsService packProductsService, PackHistoryService packHistoryService, UserService userService) {
        this.packRepository = packRepository;
        this.productService = productService;
        this.packProductsService = packProductsService;
        this.packHistoryService = packHistoryService;
        this.userService = userService;
    }

    @Override
    public Page<Pack> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return packRepository.findAllByOrderByIdDesc(pageable);

    }

    @Override
    public PackHolder getPackDetails(long id) {
        Optional<Pack> optional = packRepository.findById(id);
        Pack pack = null;
        if (optional.isPresent()) {
            pack = optional.get();
        }
        List<PackProducts> packProducts = packProductsService.getPackProducts(pack);
        PackHolder packHolder = new PackHolder();
        packHolder.setPack(pack);
        packHolder.setPackProductsList(packProducts);
        return packHolder;
    }

    @Override
    public PackHolder getEmptyPackHolder() {
        Pack pack = new Pack();
        PackHolder packHolder = new PackHolder();
        List<PackProducts> packProductsList = new ArrayList<>();
        List<Product> products = productService.getProducts(true);
        for (Product product : products) {
            PackProducts packProducts = new PackProducts();
            packProducts.setProduct(product);
            packProducts.setPack(pack);
            packProducts.setQuantity(0);
            packProductsList.add(packProducts);
        }
        packHolder.setPack(pack);
        packHolder.setPackProductsList(packProductsList);
        return packHolder;
    }

    @Override
    public PackHolder getPackHolder(long id) {
        PackHolder packHolder = getPackDetails(id);
        List<PackProducts> packProductsList = getEmptyPackHolder().getPackProductsList();
        for (PackProducts packProduct : packProductsList) {
            if (!ListUtil.isExistProductPacks(packProduct, packHolder.getPackProductsList())) {
                packHolder.getPackProductsList().add(packProduct);
            }
        }
        return packHolder;
    }

    @Override
    public Pack getPack(long id) {
        return packRepository.findById(id).orElse(null);
    }


    @Override
    @Transactional
    public Pack addPackHolder(PackHolder packHolder) {
        Pack pack = packHolder.getPack();
        List<PackProducts> packProductsList = packHolder.getPackProductsList();
        Pack addedPack = packRepository.saveAndFlush(pack);
        for (PackProducts packProduct : packProductsList) {
            int quantityProductPack = packProduct.getQuantity();
            if (quantityProductPack > 0) {
                long idProduct = packProduct.getProduct().getId();
                Product product = productService.getProduct(idProduct);
                int newReservedPacks = product.getReservedPacks() + quantityProductPack * pack.getQuantity();
                product.setReservedPacks(newReservedPacks);
                packProduct.setPack(addedPack);
                packProductsService.addPackProducts(packProduct);
                productService.addProduct(product);
            }
        }
        User user = userService.getConnectedUser();
        PackHistory packHistory = ObjectMapping.mapPackToPackHistory(addedPack);
        packHistory.setChangedBy(user.getFirstName()+" "+user.getLastName());
        packHistoryService.addPackHistory(packHistory);
        return addedPack;
    }

    @Override
    public void updatePack(Pack pack) {
        packRepository.save(pack);
    }


    @Override
    @Transactional
    public void updatePackHolder(PackHolder packHolder) {
        Pack pack = getPack(packHolder.getPack().getId());
        List<PackProducts> packProductsList = packProductsService.getPackProducts(pack);
        // update product quantities
        for (PackProducts packProduct : packProductsList) {
            int quantityProductPack = packProduct.getQuantity();
            if (quantityProductPack > 0) {
                long idProduct = packProduct.getProduct().getId();
                Product product = productService.getProduct(idProduct);
                int newReservedPacks = product.getReservedPacks() - (quantityProductPack * pack.getQuantity());
                product.setReservedPacks(newReservedPacks);
                productService.addProduct(product);
                packProductsService.deletePackProducts(pack);
            }
        }
        addPackHolder(packHolder);
    }

    @Transactional
    @Override
    public void updatePackQte(Pack pack, int selledQte, OrderNoteType orderNoteType) {
        List<PackProducts> packProductsList = packProductsService.getPackProducts(pack);
        int newPackQuantity = pack.getQuantity() - selledQte;
        int newReservedPacks = 0;
        int newProductQte = 0;
        for (PackProducts packProduct : packProductsList) {
            int quantityProductPack = packProduct.getQuantity();
            if (quantityProductPack > 0) {
                long idProduct = packProduct.getProduct().getId();
                Product product = productService.getProduct(idProduct);
                if(orderNoteType.getType().equals(OrderNoteType.EXIT.getType())) {
                    newReservedPacks = product.getReservedPacks() - (quantityProductPack * selledQte);
                    newProductQte = product.getQuantity() - (quantityProductPack * selledQte);
                }
                 // update product quantity
                product.setQuantity(newProductQte);
                // update product reserved pack
                product.setReservedPacks(newReservedPacks);
                productService.addProduct(product);
            }
        }
        // update pack real Quantity
        pack.setQuantity(newPackQuantity);
        packRepository.save(pack);
    }


    /*
    @Override
    public void updatePackQteAfterCancel(Pack pack, int orderQuantity) {
        List<PackProducts> packProductsList = packProductsService.getPackProducts(pack);
        for (PackProducts packProduct : packProductsList) {
            int quantityProductPack = packProduct.getQuantity();
            if (quantityProductPack > 0) {
                long idProduct = packProduct.getProduct().getId();
                Product product = productService.getProduct(idProduct);
                int newReservedPacks = product.getReservedPacks() + (quantityProductPack * orderQuantity);
                product.setReservedPacks(newReservedPacks);
                productService.addProduct(product);
            }
        }
        pack.setQuantity(pack.getQuantity() + orderQuantity);
        packRepository.save(pack);
    }
*/

}
