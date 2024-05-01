package com.api.Gerenciador_Lojas.controllers;

import com.api.Gerenciador_Lojas.dtos.PhysicalStoreRecordDTO;
import com.api.Gerenciador_Lojas.models.PhysicalStoreModel;
import com.api.Gerenciador_Lojas.repositories.PhysicalStoreRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/physical-store")
public class PhysicalStoreController {
    @Autowired
    PhysicalStoreRepository physicalStoreRepository;

    @PostMapping
    public ResponseEntity<Object> savePhysicalStore(@RequestBody @Valid PhysicalStoreRecordDTO physicalStoreRecordDTO){
        if(physicalStoreRepository.existsByTelephone(physicalStoreRecordDTO.telephone())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Telephone is already in use!");
        }
        if(physicalStoreRepository.existsByPhysicalAddress(physicalStoreRecordDTO.physicalAddress())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Physical Address is already in use!");
        }
        var physicalStoreModel = new PhysicalStoreModel();
        BeanUtils.copyProperties(physicalStoreRecordDTO, physicalStoreModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(physicalStoreRepository.save(physicalStoreModel));
    }

    @GetMapping
    public ResponseEntity<Page<PhysicalStoreModel>> getAllPhysicalStores(Pageable pageable){
        Page<PhysicalStoreModel> physicalStoresList = physicalStoreRepository.findAll(pageable);
        if(!physicalStoresList.isEmpty()) {
            for(PhysicalStoreModel physicalStoreModel : physicalStoresList) {
                UUID id = physicalStoreModel.getIdPhysicalStore();
                physicalStoreModel.add(linkTo(methodOn(PhysicalStoreController.class).getOnePhysicalStore(id, pageable)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(physicalStoresList);
    }

    @GetMapping("/{idPhysicalStore}")
    public ResponseEntity<Object> getOnePhysicalStore(@PathVariable(value = "idPhysicalStore") UUID idPhysicalStore, Pageable pageable){
        Optional<PhysicalStoreModel> physicalStoreModelOptional = physicalStoreRepository.findById(idPhysicalStore);
        if (!physicalStoreModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Physical Store not found!");
        }
        Page<PhysicalStoreModel> physicalStoresList = physicalStoreRepository.findAll(pageable);
        physicalStoreModelOptional.get().add(linkTo(methodOn(PhysicalStoreController.class).getAllPhysicalStores(pageable)).withRel("Physical Stores List"));
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

    @PutMapping("/{idPhysicalStore}")
    public ResponseEntity<Object> updatePhysicalStore(@PathVariable(value = "idPhysicalStore") UUID idPhysicalStore,
                                                      @RequestBody @Valid PhysicalStoreRecordDTO physicalStoreRecordDTO){
        Optional<PhysicalStoreModel> physicalStoreModelOptional = physicalStoreRepository.findById(idPhysicalStore);
        if (!physicalStoreModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Physical Store not found!");
        }
        var physicalStoreModel = new PhysicalStoreModel();
        BeanUtils.copyProperties(physicalStoreRecordDTO, physicalStoreModel);
        physicalStoreModel.setIdPhysicalStore(physicalStoreModelOptional.get().getIdPhysicalStore());
        return ResponseEntity.status(HttpStatus.OK).body(physicalStoreRepository.save(physicalStoreModel));
    }
}
