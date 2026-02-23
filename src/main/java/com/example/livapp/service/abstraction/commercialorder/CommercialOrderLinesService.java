package com.example.livapp.service.abstraction.commercialorder;

import com.example.livapp.model.commercialorder.CommercialOrder;
import com.example.livapp.model.commercialorder.CommercialOrderLine;
import com.example.livapp.model.order.OrderLine;
import java.util.List;

public interface CommercialOrderLinesService {

    List<CommercialOrderLine> getCommercialOrderLines(CommercialOrder commercialOrder);

}
