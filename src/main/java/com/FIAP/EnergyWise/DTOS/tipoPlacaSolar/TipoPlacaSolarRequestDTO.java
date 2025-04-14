package com.FIAP.EnergyWise.DTOS.tipoPlacaSolar;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TipoPlacaSolarRequestDTO {
    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    private BigDecimal potenciaWatt;

    @NotNull
    private BigDecimal precoUnitario;
}
