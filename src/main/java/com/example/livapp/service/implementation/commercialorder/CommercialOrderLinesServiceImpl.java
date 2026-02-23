package com.example.livapp.service.implementation.commercialorder;

import com.example.livapp.model.commercialorder.CommercialOrder;
import com.example.livapp.model.commercialorder.CommercialOrderLine;
import com.example.livapp.model.order.OrderLine;
import com.example.livapp.repository.commercialorder.CommercialOrderLineRepository;
import com.example.livapp.repository.commercialorder.CommercialOrderRepository;
import com.example.livapp.service.abstraction.commercialorder.CommercialOrderLinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommercialOrderLinesServiceImpl implements CommercialOrderLinesService {

    private final CommercialOrderLineRepository commercialOrderLineRepository;

    @Autowired
    public CommercialOrderLinesServiceImpl(CommercialOrderLineRepository commercialOrderLineRepository, CommercialOrderRepository commercialOrderRepository) {
        this.commercialOrderLineRepository = commercialOrderLineRepository;
    }

    @Override
    public List<CommercialOrderLine> getCommercialOrderLines(CommercialOrder commercialOrder) {
        return commercialOrderLineRepository.findCommercialOrderLinesByCommercialOrder(commercialOrder);
    }
}
