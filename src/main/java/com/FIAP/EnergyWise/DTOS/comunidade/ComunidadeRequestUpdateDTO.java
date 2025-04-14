package com.FIAP.EnergyWise.DTOS.comunidade;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComunidadeRequestUpdateDTO {
    @NotNull
    private String nome;
    @NotNull
    private int numPopulacao;
}
