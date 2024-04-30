package com.api.Gerenciador_Lojas.controllers;

import com.api.Gerenciador_Lojas.dtos.PhysicalStoreRecordDTO;
import com.api.Gerenciador_Lojas.models.PhysicalStoreModel;
import com.api.Gerenciador_Lojas.repositories.PhysicalStoreRepository;
import com.api.Gerenciador_Lojas.services.PhysicalStoreService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/physical-store")
public class PhysicalStoreController {
    @Autowired
    PhysicalStoreRepository physicalStoreRepository;
    @Autowired
    PhysicalStoreService physicalStoreService;

    @PostMapping
    public ResponseEntity<Object> savePhysicalStore(@RequestBody @Valid PhysicalStoreRecordDTO physicalStoreRecordDTO){
        if(physicalStoreService.existsByTelephone(physicalStoreRecordDTO.telephone())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Telephone is already in use!");
        }
        if(physicalStoreService.existsByPhysicalAddress(physicalStoreRecordDTO.physicalAddress())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Physical Address is already in use!");
        }
        var physicalStoreModel = new PhysicalStoreModel();
        BeanUtils.copyProperties(physicalStoreRecordDTO, physicalStoreModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(physicalStoreRepository.save(physicalStoreModel));
    }

    @GetMapping
    public ResponseEntity<Page<PhysicalStoreModel>> getAllPhysicalStores(@PageableDefault(page = 0, size = 4,
            sort = "idPhysicalStore", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(physicalStoreRepository.findAll(pageable));
    }

    @GetMapping("/{idPhysicalStore}")
    public ResponseEntity<Object> getOnePhysicalStore(@PathVariable(value = "idPhysicalStore") UUID idPhysicalStore){
        Optional<PhysicalStoreModel> physicalStoreModelOptional = physicalStoreRepository.findById(idPhysicalStore);
        if (!physicalStoreModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Physical Store not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(physicalStoreModelOptional.get());
    }

    @DeleteMapping("/{idPhysicalStore}")
    public ResponseEntity<Object> deletePhysicalStore(@PathVariable(value = "idPhysicalStore") UUID idPhysicalStore){
        Optional<PhysicalStoreModel> physicalStoreModelOptional = physicalStoreRepository.findById(idPhysicalStore);
        if (!physicalStoreModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Physical Store not found!");
        }
        physicalStoreRepository.delete(physicalStoreModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(("Physical Store deleted successfully."));
    }
}
