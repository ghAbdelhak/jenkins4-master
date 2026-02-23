package com.example.livapp.service.abstraction.commercialordernote;

import com.example.livapp.model.commercialordernote.CommercialOrderNoteLine;
import com.example.livapp.model.commercialordernote.ExitCommercialOrderNote;
import com.example.livapp.model.ordernote.OrderNote;
import com.example.livapp.model.types.OrderNoteType;

public interface CommercialOrderNoteService {


    OrderNote getCommercialOrderNote(long commercialOrderId, OrderNoteType orderNoteType);

    ExitCommercialOrderNote addExitCommercialOrderNote(ExitCommercialOrderNote exitCommercialOrderNote);

    void addCommercialOrderNoteLine(CommercialOrderNoteLine commercialOrderNoteLines);



}
