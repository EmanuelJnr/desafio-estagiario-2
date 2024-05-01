package com.api.Gerenciador_Lojas.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
//Spring Boot 3 trouxe o pacote jakarta, onde se usava o javax

//Data Tranfer Object, objetos de transferência de dados, o atributo "id" não é passado pois já é gerado automaticamente na classe Model
//O record é imutável e já traz os métodos get, toString, equals, construtor e hashCode
//Os atributos são do tipo private e final
//Anotação NotBlank o atributo não pode ser nulo, branco ou string vazia
//Anotação NotNull o atributo não pode ser nulo

public record PhysicalStoreRecordDTO(@NotBlank String CNPJ, @NotBlank String name, @NotBlank String segment,
                                     @NotBlank String telephone, @NotBlank String physicalAddress, @NotNull int numberEmployees) {
}