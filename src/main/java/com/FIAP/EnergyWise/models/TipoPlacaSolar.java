package com.FIAP.EnergyWise.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "GS1_TIPO_PLACA_SOLAR")
public class TipoPlacaSolar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_PLACA_SOLAR", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @NotNull
    @Column(name = "POTENCIA_WATT", nullable = false, precision = 12, scale = 2)
    private BigDecimal potenciaWatt;

    @NotNull
    @Column(name = "PRECO_UNITARIO", nullable = false, precision = 12, scale = 2)
    private BigDecimal precoUnitario;

}