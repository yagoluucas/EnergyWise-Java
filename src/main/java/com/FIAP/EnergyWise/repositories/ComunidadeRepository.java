package com.FIAP.EnergyWise.repositories;

import com.FIAP.EnergyWise.models.Comunidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.time.LocalDate;

public interface ComunidadeRepository extends JpaRepository<Comunidade, Long> {


}
