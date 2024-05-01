package com.api.Gerenciador_Lojas.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;
import java.io.Serializable;
import java.util.UUID;

//Essa clase é responsável por encapsular os dados da Loja Física dentro do seu escopo (POJO)
@Entity
@Table(name = "TB_PHYSICAL_STORE")//Essa anotation se comunica com o Spring que haverá uma tabela no BD para guardar seus dados

//A classe extende o RepresentationModel para ser possível a utilização dos métodos add, linkTo e methodOn para criar links de navegabilidade
public class PhysicalStoreModel extends RepresentationModel<PhysicalStoreModel> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id//Anotação para descrever que este atributo "idVirtualStore" será um ID no Banco de Dados
    @GeneratedValue(strategy = GenerationType.AUTO)//Anotação que indica o geramento aleatório de chaves, mas não repetindo
    private UUID idPhysicalStore;
    private String CNPJ;
    private String name;
    private String segment;
    private String telephone;
    private String physicalAddress;
    private int numberEmployees;

    //Abaixo vemos os Getters e Setters de cada atributo privado, para que possamos acessá-los e alterá-los
    public UUID getIdPhysicalStore() {
        return idPhysicalStore;
    }

    public void setIdPhysicalStore(UUID idPhysicalStore) {
        this.idPhysicalStore = idPhysicalStore;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public int getNumberEmployees() {
        return numberEmployees;
    }

    public void setNumberEmployees(int numberEmployees) {
        this.numberEmployees = numberEmployees;
    }
}