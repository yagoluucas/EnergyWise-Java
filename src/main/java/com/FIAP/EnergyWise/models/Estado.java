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

@Getter
@Setter
@Entity
@Table(name = "GS1_ESTADO")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTADO", nullable = false)
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "NOME", nullable = false, length = 50)
    private String nome;

}