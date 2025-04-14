package com.FIAP.EnergyWise.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "GS1_COMUNIDADE")
public class Comunidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMUNIDADE", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @NotNull
    @Column(name = "NUM_POPULACAO", nullable = false)
    private int numPopulacao;

    @Column(name = "DATA_CADASTRO")
    private Timestamp dataCadastro =
            Timestamp.valueOf(LocalDateTime.now());

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CIDADE", nullable = false)
    private Cidade cidade;

}