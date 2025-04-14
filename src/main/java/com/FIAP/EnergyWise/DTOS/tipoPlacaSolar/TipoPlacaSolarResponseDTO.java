package com.FIAP.EnergyWise.DTOS.tipoPlacaSolar;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TipoPlacaSolarResponseDTO {
    private Long id;
    private String nome;
    private BigDecimal potenciaWatt;

    private BigDecimal precoUnitario;


}
