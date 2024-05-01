package com.api.Gerenciador_Lojas.repositories;

import com.api.Gerenciador_Lojas.models.PhysicalStoreModel;
import com.api.Gerenciador_Lojas.models.VirtualStoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface VirtualStoreRepository extends JpaRepository <VirtualStoreModel, UUID>{

    boolean existsByName(String name);

    boolean existsByTelephone(String telephone);

    boolean existsByURL(String URL);
}
