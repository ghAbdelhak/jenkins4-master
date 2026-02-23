package com.example.livapp.service.abstraction.pack;

import com.example.livapp.model.types.OrderNoteType;
import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.pack.PackHolder;
import org.springframework.data.domain.Page;

public interface PackService {

    Pack addPackHolder(PackHolder packHolder);

    void updatePack(Pack pack);

    Page<Pack> findPaginated(int pageNo, int pageSize);

    PackHolder getPackDetails(long id);

    PackHolder getEmptyPackHolder();


    Pack getPack(long id);

    PackHolder getPackHolder(long id);

    void updatePackHolder(PackHolder packHolder);

    void updatePackQte(Pack pack, int selledQuantity, OrderNoteType orderNoteType);


}
