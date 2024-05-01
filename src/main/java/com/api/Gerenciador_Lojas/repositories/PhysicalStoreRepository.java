package com.api.Gerenciador_Lojas.repositories;

import com.api.Gerenciador_Lojas.models.PhysicalStoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

//Anotação semântica para indicar ao Spring que essa classe é um repositório
@Repository
public interface PhysicalStoreRepository extends JpaRepository <PhysicalStoreModel, UUID>{
    //Essa classe extende a classe JpaRepository do pacote Spring data, passando o Model do repositório e o tipo do identificador

    //Método público e abstrato, que verifica no banco de dados se existe um telefone igual o passado pelo parâmetro
    boolean existsByTelephone(String telephone);

    //Método público e abstrato, que verifica no banco de dados se existe um endereço igual o passado pelo parâmetro
    boolean existsByPhysicalAddress(String physicalAddress);
}