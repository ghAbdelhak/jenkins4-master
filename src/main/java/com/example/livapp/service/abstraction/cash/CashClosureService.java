package com.example.livapp.service.abstraction.cash;

import com.example.livapp.model.cash.CashClosure;
import org.springframework.data.domain.Page;

public interface CashClosureService {


    Page<CashClosure> getCashClosures(int pageNo, int pageSize);

    boolean isTodayCashClosed();

    void addCashClosure(CashClosure cashClosure);

    CashClosure getCashClosureById(Long id);
}
