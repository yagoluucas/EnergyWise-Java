package com.FIAP.EnergyWise.controllers;

import com.FIAP.EnergyWise.DTOS.calculoArmazenamento.CalculoArmazenamentoResponseDTO;
import com.FIAP.EnergyWise.services.CalculoArmazenamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/calculoArmazenamento")
@Tag(name = "CalculoArmazenamento", description = "API de cálculo de armazenamento, numero de placas solares e valor de placas solares")
public class CalculoArmazenamentoController {

    @Autowired
    private CalculoArmazenamentoService calculoArmazenamentoService;



    @Operation(summary = "Calcula a quantidade de placas solares necessárias para uma comunidade considerando o consumo médio mensal.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cálculo realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    @PostMapping("/{idComunidade}/{idTipoPlaca}")
    public ResponseEntity<CalculoArmazenamentoResponseDTO> calcularPlacasNecessarias(
            @PathVariable
            Long idComunidade, @PathVariable Long idTipoPlaca) {
        CalculoArmazenamentoResponseDTO calculo =
                calculoArmazenamentoService.calcularPlacasNecessarias(
                        idComunidade, idTipoPlaca);
        return ResponseEntity.status(HttpStatus.CREATED).body(calculo);
    }


    @Operation(summary = "Mostra todos os cálculos de armazenamento realizados em uma comunidade paginados e ordenados de forma decrescente pela data de criação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cálculos encontrados"),
            @ApiResponse(responseCode = "404", description = "Cálculos não encontrados")
    })
    @GetMapping("/comunidade/{idComunidade}")
    public ResponseEntity<List<EntityModel<CalculoArmazenamentoResponseDTO>>> findCalculoByComunidade(
            @PathVariable Long idComunidade,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {


        Page<CalculoArmazenamentoResponseDTO> calculosPage =
                calculoArmazenamentoService.findAllCalculosByComunidade(
                        idComunidade, page, size);

        List<EntityModel<CalculoArmazenamentoResponseDTO>> pagedModel = calculosPage.stream()
                .map(calculo -> EntityModel.of(calculo,
                        linkTo(methodOn(
                                CalculoArmazenamentoController.class)
                                .findCalculoById(calculo.getId())).withSelfRel(),
                        linkTo(methodOn(
                                CalculoArmazenamentoController.class)
                                .findCalculoByComunidade(idComunidade, page, size)).withRel("calculos")
                )).collect(toList());


        return ResponseEntity.status(HttpStatus.OK).body(pagedModel);
    }

    @Operation(summary = "Busca um cálculo de armazenamento por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cálculo encontrado"),
            @ApiResponse(responseCode = "404", description = "Cálculo não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CalculoArmazenamentoResponseDTO>> findCalculoById(
            @PathVariable Long id) {

        CalculoArmazenamentoResponseDTO calculo =
                calculoArmazenamentoService.findCalculoById(id);
        EntityModel<CalculoArmazenamentoResponseDTO> entityModel =
                EntityModel.of(calculo,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                                        CalculoArmazenamentoController.class)
                                .findCalculoById(id)).withSelfRel());

        return ResponseEntity.ok(entityModel);
    }
}
