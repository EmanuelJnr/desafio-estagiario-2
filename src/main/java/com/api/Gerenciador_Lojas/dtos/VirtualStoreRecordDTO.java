package com.api.Gerenciador_Lojas.dtos;

import jakarta.validation.constraints.NotBlank;

public record VirtualStoreRecordDTO(@NotBlank String CNPJ, @NotBlank String name, @NotBlank String segment,
                                    @NotBlank String telephone, @NotBlank String URL, @NotBlank String assessment) {
}
