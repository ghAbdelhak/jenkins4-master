package com.example.livapp.service.implementation.pack;

import com.example.livapp.model.pack.PackHistory;
import com.example.livapp.repository.pack.PackHistoryRepository;
import com.example.livapp.service.abstraction.pack.PackHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackHistoryServiceImpl implements PackHistoryService {

    private final PackHistoryRepository packHistoryRepository;

    @Autowired
    public PackHistoryServiceImpl(PackHistoryRepository packHistoryRepository) {
        this.packHistoryRepository = packHistoryRepository;
    }

    @Override
    public void addPackHistory(PackHistory packHistory) {
        packHistoryRepository.save(packHistory);
    }

}
