package com.example.livapp.service.implementation.cash;

import com.example.livapp.model.cash.CashClosure;
import com.example.livapp.model.order.Order;
import com.example.livapp.repository.cash.CashClosureRepository;
import com.example.livapp.service.abstraction.cash.CashClosureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CashClosureServiceImpl implements CashClosureService {

    private final CashClosureRepository cashClosureRepository;

    @Autowired
    public CashClosureServiceImpl(CashClosureRepository cashClosureRepository) {
        this.cashClosureRepository = cashClosureRepository;
    }


    @Override
    public Page<CashClosure> getCashClosures(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return cashClosureRepository.findCashClosureByOrderByIdDesc(pageable);
    }

    @Override
    public boolean isTodayCashClosed() {
        return cashClosureRepository.isTodayCashClosed()==1;
    }

    @Override
    public void addCashClosure(CashClosure cashClosure) {
        cashClosureRepository.save(cashClosure);
    }

    @Override
    public CashClosure getCashClosureById(Long id) {

        return cashClosureRepository.findById(id).orElse(null);
    }
}
