package com.api.Gerenciador_Lojas.repositories;

import com.api.Gerenciador_Lojas.models.PhysicalStoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhysicalStoreRepository extends JpaRepository <PhysicalStoreModel, UUID>{

}
