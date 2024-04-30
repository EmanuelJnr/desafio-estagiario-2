package com.api.Gerenciador_Lojas.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PhysicalStoreRecordDTO(@NotBlank String CNPJ, @NotBlank String name, @NotBlank String segment,
                                     @NotBlank String telephone, @NotBlank String physicalAddress, @NotNull int numberEmployees) {
}
