package com.FIAP.EnergyWise.DTOS.comunidade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComunidadeResponseDTO
{
    private Long id;
    private String nome;
    private Integer numPopulacao;
    private String nomeCidade;
    private Timestamp dataCadastro;

    public Long getId() {
        return this.id;
    }
}
