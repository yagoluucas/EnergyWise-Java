package com.FIAP.EnergyWise.controllers;

import com.FIAP.EnergyWise.DTOS.tipoPlacaSolar.TipoPlacaSolarRequestDTO;
import com.FIAP.EnergyWise.DTOS.tipoPlacaSolar.TipoPlacaSolarResponseDTO;
import com.FIAP.EnergyWise.models.TipoPlacaSolar;
import com.FIAP.EnergyWise.services.TipoPlacaSolarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/tipoPlacaSolar")
@Tag(name = "TipoPlacaSolar", description = "API de tipos de placas solares. Representa os tipos de placas solares disponíveis para utilização em uma comunidade.")
public class TipoPlacaSolarController {

    @Autowired
    private TipoPlacaSolarService tipoPlacaSolarService;

    @Operation(summary = "Buscar todos os tipos de placas solares paginados e ordenados de forma crescente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos de placas solares encontrados"),
            @ApiResponse(responseCode = "404", description = "Tipos de placas solares não encontrados")
    })
    @GetMapping
    public ResponseEntity<List<EntityModel<TipoPlacaSolar>>> findAllPlacasSolares(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<TipoPlacaSolar> placasSolares = tipoPlacaSolarService.findAllPlacasSolares(page, size);
        if (placasSolares.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(placasSolares.stream()
                .map(placaSolar -> EntityModel.of(placaSolar, linkTo(methodOn(TipoPlacaSolarController.class)
                        .findById(placaSolar.getId())).withSelfRel())).toList());
    }



    @Operation(summary = "Buscar um tipo de placa solar por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de placa solar encontrado"),
            @ApiResponse(responseCode = "404", description = "Tipo de placa solar não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TipoPlacaSolarResponseDTO>> findById(@PathVariable Long id) {
        TipoPlacaSolarResponseDTO tipoPlacaSolar = tipoPlacaSolarService.findById(id);
        if (tipoPlacaSolar == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(EntityModel.of(tipoPlacaSolar, linkTo(methodOn(TipoPlacaSolarController.class)
                        .findById(id)).withSelfRel()));
    }



    @Operation(summary = "Cadastra um novo tipo de placa solar passando o nome, potência em watts e preço unitário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de placa solar cadastrado"),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar o tipo de placa solar")
    })
    @PostMapping
    public ResponseEntity<TipoPlacaSolarResponseDTO> createTipoPlacaSolar(
            TipoPlacaSolarRequestDTO tipoPlacaSolar) {
        TipoPlacaSolarResponseDTO tipoPlacaSolarCriado = tipoPlacaSolarService.inserirTipoPlacaSolar(tipoPlacaSolar);
        if (tipoPlacaSolarCriado == null) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(201).body(tipoPlacaSolarCriado);
    }
}
