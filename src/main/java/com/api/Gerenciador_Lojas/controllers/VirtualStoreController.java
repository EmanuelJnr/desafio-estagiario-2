package com.api.Gerenciador_Lojas.controllers;

import com.api.Gerenciador_Lojas.dtos.VirtualStoreRecordDTO;
import com.api.Gerenciador_Lojas.models.VirtualStoreModel;
import com.api.Gerenciador_Lojas.repositories.VirtualStoreRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/virtual-store")
public class VirtualStoreController {
    @Autowired
    VirtualStoreRepository virtualStoreRepository;

    @PostMapping
    public ResponseEntity<Object> saveVirtualStore(@RequestBody @Valid VirtualStoreRecordDTO virtualStoreRecordDTO){
        if(virtualStoreRepository.existsByName(virtualStoreRecordDTO.name())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Name is already in use!");
        }
        if(virtualStoreRepository.existsByTelephone(virtualStoreRecordDTO.telephone())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Telephone is already in use!");
        }
        if(virtualStoreRepository.existsByURL(virtualStoreRecordDTO.URL())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: URL is already in use!");
        }
        var virtualStoreModel = new VirtualStoreModel();
        BeanUtils.copyProperties(virtualStoreRecordDTO, virtualStoreModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(virtualStoreRepository.save(virtualStoreModel));
    }

    @GetMapping
    public ResponseEntity<Page<VirtualStoreModel>> getAllVirtualStores(Pageable pageable){
        Page<VirtualStoreModel> virtualStoresList = virtualStoreRepository.findAll(pageable);
        if(!virtualStoresList.isEmpty()) {
            for(VirtualStoreModel virtualStoreModel : virtualStoresList) {
                UUID id = virtualStoreModel.getIdVirtualStore();
                virtualStoreModel.add(linkTo(methodOn(VirtualStoreController.class).getOneVirtualStore(id, pageable)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(virtualStoresList);
    }

    @GetMapping("/{idVirtualStore}")
    public ResponseEntity<Object> getOneVirtualStore(@PathVariable(value = "idVirtualStore") UUID idVirtualStore, Pageable pageable){
        Optional<VirtualStoreModel> virtualStoreModelOptional = virtualStoreRepository.findById(idVirtualStore);
        if (!virtualStoreModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Virtual Store not found!");
        }
        Page<VirtualStoreModel> virtualStoresList = virtualStoreRepository.findAll(pageable);
        virtualStoreModelOptional.get().add(linkTo(methodOn(VirtualStoreController.class).getAllVirtualStores(pageable)).withRel("Virtual Stores List"));
        return ResponseEntity.status(HttpStatus.OK).body(virtualStoreModelOptional.get());
    }

    @DeleteMapping("/{idVirtualStore}")
    public ResponseEntity<Object> deleteVirtualStore(@PathVariable(value = "idVirtualStore") UUID idVirtualStore){
        Optional<VirtualStoreModel> virtualStoreModelOptional = virtualStoreRepository.findById(idVirtualStore);
        if (!virtualStoreModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Virtual Store not found!");
        }
        virtualStoreRepository.delete(virtualStoreModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(("Virtual Store deleted successfully."));
    }

    @PutMapping("/{idVirtualStore}")
    public ResponseEntity<Object> updateVirtualStore(@PathVariable(value = "idVirtualStore") UUID idVirtualStore,
                                                      @RequestBody @Valid VirtualStoreRecordDTO virtualStoreRecordDTO){
        Optional<VirtualStoreModel> virtualStoreModelOptional = virtualStoreRepository.findById(idVirtualStore);
        if (!virtualStoreModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Virtual Store not found!");
        }
        var virtualStoreModel = new VirtualStoreModel();
        BeanUtils.copyProperties(virtualStoreRecordDTO, virtualStoreModel);
        virtualStoreModel.setIdVirtualStore(virtualStoreModelOptional.get().getIdVirtualStore());
        return ResponseEntity.status(HttpStatus.OK).body(virtualStoreRepository.save(virtualStoreModel));
    }
}
