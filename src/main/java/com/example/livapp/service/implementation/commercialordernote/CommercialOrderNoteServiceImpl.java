package com.example.livapp.service.implementation.commercialordernote;

import com.example.livapp.model.commercialordernote.CommercialOrderNoteLine;
import com.example.livapp.model.commercialordernote.ExitCommercialOrderNote;
import com.example.livapp.model.ordernote.OrderNote;
import com.example.livapp.model.types.OrderNoteType;
import com.example.livapp.repository.commercialordernote.CommercialOrderNoteLineRepository;
import com.example.livapp.repository.commercialordernote.ExitCommercialOrderNoteRepository;
import com.example.livapp.service.abstraction.commercialordernote.CommercialOrderNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommercialOrderNoteServiceImpl implements CommercialOrderNoteService {


    private final CommercialOrderNoteLineRepository commercialOrderNoteLineRepository;
    private final ExitCommercialOrderNoteRepository exitCommercialOrderNoteRepository;

    @Autowired
    public CommercialOrderNoteServiceImpl(
    CommercialOrderNoteLineRepository commercialOrderNoteLineRepository
    , ExitCommercialOrderNoteRepository exitCommercialOrderNoteRepository) {
        this.commercialOrderNoteLineRepository = commercialOrderNoteLineRepository;
        this.exitCommercialOrderNoteRepository = exitCommercialOrderNoteRepository;
    }

    @Override
    public OrderNote getCommercialOrderNote (long commercialOrderId, OrderNoteType orderNoteType) {
        OrderNote orderNote = null;
        switch (orderNoteType) {
            case EXIT: {
                orderNote = exitCommercialOrderNoteRepository.findByCommercialOrderId(commercialOrderId);
                break;
            }

        }
        return orderNote;

    }

    @Override
    public ExitCommercialOrderNote addExitCommercialOrderNote(ExitCommercialOrderNote exitCommercialOrderNote) {
        return exitCommercialOrderNoteRepository.save(exitCommercialOrderNote);

    }

    @Override
    public void addCommercialOrderNoteLine(CommercialOrderNoteLine commercialOrderNoteLine) {
        commercialOrderNoteLineRepository.save(commercialOrderNoteLine);

    }


}
