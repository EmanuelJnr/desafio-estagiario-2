package com.api.Gerenciador_Lojas.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_PHYSICAL_STORE")
public class PhysicalStoreModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idPhysicalStore;
    private String CNPJ;
    private String name;
    private String segment;
    private String telephone;
    private String physicalAddress;
    private int numberEmployees;

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
