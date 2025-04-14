package com.FIAP.EnergyWise.services;

import com.FIAP.EnergyWise.DTOS.tipoPlacaSolar.TipoPlacaSolarRequestDTO;
import com.FIAP.EnergyWise.DTOS.tipoPlacaSolar.TipoPlacaSolarResponseDTO;
import com.FIAP.EnergyWise.exception.ResourceNotFoundException;
import com.FIAP.EnergyWise.models.TipoPlacaSolar;
import com.FIAP.EnergyWise.repositories.TipoPlacaSolarRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TipoPlacaSolarService {

    @Autowired
    private TipoPlacaSolarRepository tipoPlacaSolarRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public TipoPlacaSolarResponseDTO inserirTipoPlacaSolar(
            TipoPlacaSolarRequestDTO requestDTO) {
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("INSERIR_TIPO_PLACA_SOLAR");

            // Registrar os parâmetros
            query.registerStoredProcedureParameter("p_nome", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_potencia_watt", BigDecimal.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_preco_unitario", BigDecimal.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_id_tipo_placa", Long.class, ParameterMode.OUT);

            // Definir os valores
            query.setParameter("p_nome", requestDTO.getNome());
            query.setParameter("p_potencia_watt", requestDTO.getPotenciaWatt());
            query.setParameter("p_preco_unitario", requestDTO.getPrecoUnitario());

            // Executar a procedure
            query.execute();

            // Obter o ID gerado
            Long idTipoPlaca = ((Number) query.getOutputParameterValue("p_id_tipo_placa")).longValue();

            // Construir o DTO de resposta
            TipoPlacaSolarResponseDTO responseDTO = new TipoPlacaSolarResponseDTO();
            responseDTO.setId(idTipoPlaca);
            responseDTO.setNome(requestDTO.getNome());
            responseDTO.setPotenciaWatt(requestDTO.getPotenciaWatt());
            responseDTO.setPrecoUnitario(requestDTO.getPrecoUnitario());

            return responseDTO;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir tipo de placa solar: " + e.getMessage(), e);
        }
    }


    public Page<TipoPlacaSolar> findAllPlacasSolares(int page, int size) {

        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id").ascending());

        Page<TipoPlacaSolar> tipoPlacaSolares = tipoPlacaSolarRepository.findAll(
                pageable);

        if (tipoPlacaSolares.isEmpty()) {
            new ResourceNotFoundException("Nenhum tipo de placa solar encontrado");
        }
        return tipoPlacaSolares;
    }

    public TipoPlacaSolarResponseDTO findById(Long id) {
        TipoPlacaSolar tipoPlacaSolar = tipoPlacaSolarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de placa solar não encontrado"));
        TipoPlacaSolarResponseDTO responseDTO = modelMapper.map(tipoPlacaSolar, TipoPlacaSolarResponseDTO.class);
        return responseDTO;
    }



}
