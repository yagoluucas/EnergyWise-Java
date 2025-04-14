package com.FIAP.EnergyWise.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "GS1_CALCULO_ARMAZENAMENTO")
public class CalculoArmazenamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CALCULO_ARMAZENAMENTO", nullable = false)
    private Long id;

    @Column(name = "DATA_CALCULO")
    private Timestamp dataCalculo;

    @Column(name = "ARMAZENAMENTO_MENSAL", precision = 12, scale = 2)
    private BigDecimal capacidadeArmazenamento;

    @Size(max = 500)
    @Column(name = "OBSERVACAO", length = 500)
    private String observacao;

    @Column(name = "QTD_PLACAS", precision = 12, scale = 2)
    private BigDecimal qtdPlacas;

    @Column(name = "CUSTO_PLACAS", precision = 12, scale = 2)
    private BigDecimal custoPlacas;

    // Relacionamento com a tabela GS1_COMUNIDADE
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_COMUNIDADE", nullable = false)
    private Comunidade comunidade;

    // Relacionamento com a tabela GS1_TIPO_PLACA_SOLAR
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_TIPO_PLACA_SOLAR")
    private TipoPlacaSolar tipoPlacaSolar;

}