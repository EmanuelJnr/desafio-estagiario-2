package com.api.Gerenciador_Lojas.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_VIRTUAL_STORE")
public class VirtualStoreModel extends RepresentationModel<VirtualStoreModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idVirtualStore;
    private String CNPJ;
    private String name;
    private String segment;
    private String telephone;
    private String URL;
    private String assessment;

    public UUID getIdVirtualStore() {
        return idVirtualStore;
    }

    public void setIdVirtualStore(UUID idVirtualStore) {
        this.idVirtualStore = idVirtualStore;
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

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }
}
