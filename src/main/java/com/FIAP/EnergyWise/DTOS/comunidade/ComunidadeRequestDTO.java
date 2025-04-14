package com.FIAP.EnergyWise.DTOS.comunidade;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComunidadeRequestDTO {
    @NotNull
    private String nome;
    @NotNull
    private int numPopulacao;
    @NotNull
    @NotEmpty
    private String nomeCidade;
}
