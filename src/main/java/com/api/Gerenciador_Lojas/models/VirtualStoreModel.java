package com.api.Gerenciador_Lojas.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;
import java.io.Serializable;
import java.util.UUID;

//Essa clase é responsável por encapsular os dados da Loja Virtual dentro do seu escopo (POJO)
@Entity
@Table(name = "TB_VIRTUAL_STORE")//Essa anotation se comunica com o Spring que haverá uma tabela no BD para guardar seus dados

//A classe extende o RepresentationModel para ser possível a utilização dos métodos add, linkTo e methodOn para criar links de navegabilidade
public class VirtualStoreModel extends RepresentationModel<VirtualStoreModel> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id//Anotação para descrever que este atributo "idVirtualStore" será um ID no Banco de Dados
    @GeneratedValue(strategy = GenerationType.AUTO)//Anotação que indica o geramento aleatório de chaves, mas não repetindo
    private UUID idVirtualStore;
    private String CNPJ;
    private String name;
    private String segment;
    private String telephone;
    private String URL;
    private String assessment;

    //Abaixo vemos os Getters e Setters de cada atributo privado, para que possamos acessá-los e alterá-los
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