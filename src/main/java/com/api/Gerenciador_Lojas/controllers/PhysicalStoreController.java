package com.api.Gerenciador_Lojas.controllers;

import com.api.Gerenciador_Lojas.dtos.PhysicalStoreRecordDTO;
import com.api.Gerenciador_Lojas.models.PhysicalStoreModel;
import com.api.Gerenciador_Lojas.repositories.PhysicalStoreRepository;
import com.api.Gerenciador_Lojas.services.PhysicalStoreService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/physical-store")
public class PhysicalStoreController {
    @Autowired
    PhysicalStoreService physicalStoreService;

    @PostMapping
    public ResponseEntity<Object> savePhysicalStore(@RequestBody @Valid PhysicalStoreRecordDTO physicalStoreRecordDTO){
        var physicalStoreModel = new PhysicalStoreModel();
        BeanUtils.copyProperties(physicalStoreRecordDTO, physicalStoreModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(physicalStoreService.save(physicalStoreModel));
    }
}
