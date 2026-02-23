package com.example.livapp.model.commercialorder;

import java.util.List;

public class CommercialOrderHolder {

    private CommercialOrder commercialOrder;

    private List<CommercialOrderLine> CommercialOrderLines;


    public CommercialOrder getCommercialOrder() {
        return commercialOrder;
    }

    public void setCommercialOrder(CommercialOrder commercialOrder) {
        this.commercialOrder = commercialOrder;
    }

    public List<CommercialOrderLine> getCommercialOrderLines() {
        return CommercialOrderLines;
    }

    public void setCommercialOrderLines(List<CommercialOrderLine> commercialOrderLines) {
        CommercialOrderLines = commercialOrderLines;
    }
}
