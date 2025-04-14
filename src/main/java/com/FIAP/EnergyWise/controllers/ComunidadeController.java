package com.FIAP.EnergyWise.controllers;

import com.FIAP.EnergyWise.DTOS.comunidade.ComunidadeRequestDTO;
import com.FIAP.EnergyWise.DTOS.comunidade.ComunidadeRequestUpdateDTO;
import com.FIAP.EnergyWise.DTOS.comunidade.ComunidadeResponseDTO;
import com.FIAP.EnergyWise.services.ComunidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/comunidade")
@Tag(name = "Comunidade", description = "API de comunidades. Apesar do nome 'Comunidade', a entidade representa um condomínio de casas ou um conjunto de prédios.")
public class ComunidadeController {


    @Autowired
    private ComunidadeService comunidadeService;


    @Operation(summary = "Cria uma comunidade com base no nome da cidade, nome da comunidade e número de habitantes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comunidade criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    @PostMapping
    public ResponseEntity<ComunidadeResponseDTO> createComunidade(@RequestBody
                                                       ComunidadeRequestDTO comunidadeRequestDTO) {
        ComunidadeResponseDTO comunidade = comunidadeService.createComunidade(
                comunidadeRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(comunidade);
    }

    @Operation(summary = "Busca todas as comunidades paginadas e ordenadas de forma crescente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunidades encontradas"),
            @ApiResponse(responseCode = "404", description = "Comunidades não encontradas")
    })
    @GetMapping
    public ResponseEntity<List<EntityModel<ComunidadeResponseDTO>>> findAllComunidades(
            @RequestParam (defaultValue = "1") int page,
            @RequestParam (defaultValue = "10") int size) {

        Page<ComunidadeResponseDTO> comunidadesPage = comunidadeService.findAllComunidades(page, size);

        List<EntityModel<ComunidadeResponseDTO>> pagedModel = comunidadesPage.stream()
                .map(comunidade -> EntityModel.of(comunidade, linkTo(methodOn(ComunidadeController.class)
                        .findComunidadeById(comunidade.getId())).withSelfRel()))
                .collect(toList()
        );

        return ResponseEntity.ok(pagedModel);
    }


    @Operation(summary = "Busca uma comunidade por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunidade encontrada"),
            @ApiResponse(responseCode = "404", description = "Comunidade não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ComunidadeResponseDTO>> findComunidadeById(@PathVariable Long id) {
        ComunidadeResponseDTO comunidade = comunidadeService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(EntityModel.of(comunidade, linkTo(methodOn(ComunidadeController.class)
                .findComunidadeById(id)).withSelfRel()));
    }


    @Operation(summary = "Atualiza uma comunidade. Apenas o nome da comunidade e o número de habitantes podem ser atualizados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunidade atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comunidade não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ComunidadeResponseDTO> updateComunidade(@PathVariable Long id, @RequestBody
    ComunidadeRequestUpdateDTO comunidadeRequestDTO) {
        ComunidadeResponseDTO comunidade = comunidadeService.updateComunidade(id,
                comunidadeRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(comunidade);
    }

    @Operation(summary = "Deleta uma comunidade por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunidade deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comunidade não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ComunidadeResponseDTO> deleteComunidade(@PathVariable Long id) {
        ComunidadeResponseDTO comunidade = comunidadeService.deleteComunidade(id);
        return ResponseEntity.status(HttpStatus.OK).body(comunidade);
    }


}
