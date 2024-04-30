package com.api.Gerenciador_Lojas.services;

import com.api.Gerenciador_Lojas.models.PhysicalStoreModel;
import com.api.Gerenciador_Lojas.repositories.PhysicalStoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhysicalStoreService {
    @Autowired
    PhysicalStoreRepository physicalStoreRepository;

    @Transactional
    public PhysicalStoreModel save(PhysicalStoreModel physicalStoreModel) {
        return physicalStoreRepository.save(physicalStoreModel);
    }
}
