package com.FIAP.EnergyWise.DTOS.consumo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
public class ConsumoResponseDTO {
    private Long id;
    private Timestamp dataConsumo;
    private BigDecimal energiaConsumida;
    private String nomeComunidade;
}
