package com.FIAP.EnergyWise.repositories;

import com.FIAP.EnergyWise.models.ComunidadePopulacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ComunidadePopulacaoRepository extends JpaRepository<ComunidadePopulacao, Long>
{
    @Query(value = "SELECT * FROM GS1_COMUNIDADE_POPULACAO WHERE comunidade_id = :idComunidade AND data = (SELECT MAX(data) FROM GS1_COMUNIDADE_POPULACAO WHERE comunidade_id = :idComunidade)", nativeQuery = true)
    ComunidadePopulacao findComunidadePopulacaoByComunidadeIdAAndLastData(long idComunidade);
}
