package com.FIAP.EnergyWise.DTOS.calculoArmazenamento;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
public class CalculoArmazenamentoResponseDTO {
    private long id;
    private Timestamp dataCalculo;
    private BigDecimal capacidadeArmazenamento;
    private String observacao;
    private BigDecimal qtdPlacas;
    private BigDecimal custoPlacas;
    private String comunidade;
    private String tipoPlacaSolar;

    public Long getId() {
        return this.id;
    }
}
