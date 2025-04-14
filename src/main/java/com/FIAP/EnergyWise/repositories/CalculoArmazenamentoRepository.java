package com.FIAP.EnergyWise.repositories;

import com.FIAP.EnergyWise.models.CalculoArmazenamento;
import org.hibernate.result.Output;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CalculoArmazenamentoRepository extends JpaRepository<CalculoArmazenamento, Long>
{
    Optional<Page<CalculoArmazenamento>> findCalculoArmazenamentoByComunidadeId(Long idComunidade,
                                                                                Pageable pageable);

    @Query(value = "SELECT * FROM GS1_CALCULO_ARMAZENAMENTO WHERE ID_COMUNIDADE = :idComunidade AND DATA_CALCULO = (SELECT MAX(DATA_CALCULO) FROM GS1_CALCULO_ARMAZENAMENTO WHERE ID_COMUNIDADE = :idComunidade)", nativeQuery = true)
    Optional<CalculoArmazenamento> findLastCalculoArmazenamentoByComunidadeId(Long idComunidade);


}
