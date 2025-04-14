package com.FIAP.EnergyWise.services;

import com.FIAP.EnergyWise.DTOS.consumo.ConsumoRequestDTO;
import com.FIAP.EnergyWise.DTOS.consumo.ConsumoResponseDTO;
import com.FIAP.EnergyWise.exception.ResourceNotFoundException;
import com.FIAP.EnergyWise.models.Comunidade;
import com.FIAP.EnergyWise.models.Consumo;
import com.FIAP.EnergyWise.repositories.ComunidadeRepository;
import com.FIAP.EnergyWise.repositories.ConsumoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsumoService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ConsumoRepository consumoRepository;

    @Autowired
    private ComunidadeRepository comunidadeRepository;

    public ConsumoResponseDTO createConsumo(ConsumoRequestDTO consumoRequestDTO,
                                            Long idComunidade) {
        try {
            // Obter a comunidade
            Comunidade comunidade = comunidadeRepository.findById(idComunidade)
                    .orElseThrow(() -> new RuntimeException(
                            "Comunidade não encontrada"));

            // Definir a data atual
            Timestamp dataConsumo = Timestamp.valueOf(LocalDateTime.now());

            // Chamar a procedure INSERIR_CONSUMO
            StoredProcedureQuery query =
                    entityManager.createStoredProcedureQuery("INSERIR_CONSUMO");

            // Registrar os parâmetros de entrada
            query.registerStoredProcedureParameter("p_data_consumo",
                    Timestamp.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_energia_consumida",
                    BigDecimal.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_id_comunidade",
                    Long.class, ParameterMode.IN);

            // **Registrar o parâmetro de saída**
            query.registerStoredProcedureParameter("p_id_consumo", Long.class,
                    ParameterMode.OUT);

            // Definir os valores dos parâmetros de entrada
            query.setParameter("p_data_consumo", dataConsumo);
            query.setParameter("p_energia_consumida",
                    consumoRequestDTO.getEnergiaConsumida());
            query.setParameter("p_id_comunidade", idComunidade);

            // Executar a procedure
            query.execute();

            // Obter o ID gerado do consumo
            Long idConsumo = ((Number) query.getOutputParameterValue(
                    "p_id_consumo")).longValue();

            // Construir o DTO de resposta
            ConsumoResponseDTO consumoResponseDTO = new ConsumoResponseDTO();
            consumoResponseDTO.setId(idConsumo);
            consumoResponseDTO.setDataConsumo(dataConsumo);
            consumoResponseDTO.setEnergiaConsumida(
                    consumoRequestDTO.getEnergiaConsumida());
            consumoResponseDTO.setNomeComunidade(comunidade.getNome());

            return consumoResponseDTO;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao inserir consumo: " + e.getMessage(), e);
        }
    }


    public Page<ConsumoResponseDTO> findAllConsumos(int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size,
                Sort.by("dataConsumo").descending());

        Page<Consumo> consumo = consumoRepository.findAll(pageable);
        if (consumo.isEmpty()) {
            throw new RuntimeException("Nenhum consumo encontrado");
        }

        Page<ConsumoResponseDTO> consumoResponse = consumo.map(c -> {
            ConsumoResponseDTO consumoResponseDTO =
                    modelMapper.map(c, ConsumoResponseDTO.class);
            consumoResponseDTO.setNomeComunidade(
                    c.getComunidade().getNome());
            return consumoResponseDTO;
        });
        return consumoResponse;
    }
        public Page<ConsumoResponseDTO> findConsumoByComunidade (Long
        idComunidade,int page, int size){

            Pageable pageable =
                    PageRequest.of(page - 1, size, Sort.by("id").ascending());

            Page<Consumo> consumos =
                    consumoRepository.findConsumoByComunidadeId(idComunidade,
                                    pageable)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Consumos não encontrados para a comunidade."));

            Page<ConsumoResponseDTO> consumosResponse = consumos.map(c -> {
                ConsumoResponseDTO consumoResponseDTO =
                        modelMapper.map(c, ConsumoResponseDTO.class);
                consumoResponseDTO.setNomeComunidade(
                        c.getComunidade().getNome());
                return consumoResponseDTO;
            });
            return consumosResponse;
        }


    }
