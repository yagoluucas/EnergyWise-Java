package com.FIAP.EnergyWise.repositories;

import com.FIAP.EnergyWise.models.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CidadeRepository extends JpaRepository<Cidade, Long>
{
    Optional<Cidade> findCidadeByNome(String nomeCidade);
}
