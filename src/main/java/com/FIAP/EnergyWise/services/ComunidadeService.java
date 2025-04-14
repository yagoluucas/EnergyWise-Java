package com.FIAP.EnergyWise.services;

import com.FIAP.EnergyWise.DTOS.comunidade.ComunidadeRequestDTO;
import com.FIAP.EnergyWise.DTOS.comunidade.ComunidadeRequestUpdateDTO;
import com.FIAP.EnergyWise.DTOS.comunidade.ComunidadeResponseDTO;
import com.FIAP.EnergyWise.exception.ResourceNotFoundException;
import com.FIAP.EnergyWise.models.Cidade;
import com.FIAP.EnergyWise.models.Comunidade;
import com.FIAP.EnergyWise.repositories.CidadeRepository;
import com.FIAP.EnergyWise.repositories.ComunidadeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComunidadeService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ComunidadeRepository comunidadeRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EntityManager entityManager;

    public ComunidadeResponseDTO createComunidade(ComunidadeRequestDTO comunidadeRequestDTO) {

        // Validar se a população é maior que zero
        if (comunidadeRequestDTO.getNumPopulacao() <= 0) {
            throw new ResourceNotFoundException("A comunidade deve ter pelo menos uma pessoa");
        }

        // Obter a cidade
        Cidade cidade = cidadeRepository.findCidadeByNome(
                        comunidadeRequestDTO.getNomeCidade().toUpperCase())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cidade não encontrada. " +
                                "A cidade não está cadastrada ou o nome da cidade está incorreto. " +
                                "Por favor, verifique o nome da cidade, incluindo acentos e caracteres especiais."));

        try {
            // Chamar a procedure INSERIR_COMUNIDADE usando EntityManager
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("INSERIR_COMUNIDADE");

            // Registrar os parâmetros de entrada e saída
            query.registerStoredProcedureParameter("p_nome", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_num_populacao", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_id_cidade", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_data_cadastro", Timestamp.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_id_comunidade", Long.class, ParameterMode.OUT);

            // Definir os valores dos parâmetros de entrada
            query.setParameter("p_nome", comunidadeRequestDTO.getNome());
            query.setParameter("p_num_populacao", comunidadeRequestDTO.getNumPopulacao());
            query.setParameter("p_id_cidade", cidade.getId());
            query.setParameter("p_data_cadastro", Timestamp.valueOf(LocalDate.now().atStartOfDay()));

            // Executar a procedure
            query.execute();

            // Obter o ID gerado
            Long idComunidade = ((Number) query.getOutputParameterValue("p_id_comunidade")).longValue();

            // Obter a comunidade inserida (opcional, se quiser retornar todos os dados)
            Comunidade comunidadeInserida = entityManager.find(Comunidade.class, idComunidade);

            // Mapear a comunidade inserida para o DTO de resposta
            ComunidadeResponseDTO comunidadeResponseDTO = modelMapper.map(comunidadeInserida, ComunidadeResponseDTO.class);

            return comunidadeResponseDTO;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir comunidade: " + e.getMessage(), e);
        }
    }

    public Page<ComunidadeResponseDTO> findAllComunidades(int page, int size) {

        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id").ascending());
        Page<Comunidade> comunidadesPage = comunidadeRepository.findAll(pageable);

        if (comunidadesPage.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma comunidade cadastrada");
        }

        // Mapeia diretamente cada entidade para DTO dentro do Page
        Page<ComunidadeResponseDTO> comunidadesResponsePage = comunidadesPage.map(comunidade -> {
            ComunidadeResponseDTO dto = modelMapper.map(comunidade, ComunidadeResponseDTO.class);
            dto.setNomeCidade(comunidade.getCidade().getNome()); // Definindo apenas o nome da cidade
            return dto;
        });

        return comunidadesResponsePage;
    }

    public ComunidadeResponseDTO findById(Long id) {
        Comunidade comunidade = comunidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comunidade não encontrada"));
        ComunidadeResponseDTO dto =
                modelMapper.map(comunidade, ComunidadeResponseDTO.class);
        dto.setNomeCidade(comunidade.getCidade().getNome());
        return dto;
    }

    @Transactional
    public ComunidadeResponseDTO updateComunidade(Long id,
                                                  ComunidadeRequestUpdateDTO comunidadeRequestDTO) {
        Comunidade comunidade = comunidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comunidade não encontrada"));

        comunidade.setNome(comunidadeRequestDTO.getNome());
        comunidade.setNumPopulacao(comunidadeRequestDTO.getNumPopulacao());

        comunidadeRepository.save(comunidade);

        ComunidadeResponseDTO dto =
                modelMapper.map(comunidade, ComunidadeResponseDTO.class);
        dto.setNomeCidade(comunidade.getCidade().getNome());
        return dto;

    }

    public ComunidadeResponseDTO deleteComunidade(Long id) {
        Comunidade comunidade = comunidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comunidade não encontrada"));
        comunidadeRepository.delete(comunidade);

        ComunidadeResponseDTO dto =
                modelMapper.map(comunidade, ComunidadeResponseDTO.class);
        dto.setNomeCidade(comunidade.getCidade().getNome());
        return dto;
    }


}
