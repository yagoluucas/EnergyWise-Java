package com.FIAP.EnergyWise.controllers;

import com.FIAP.EnergyWise.DTOS.consumo.ConsumoRequestDTO;
import com.FIAP.EnergyWise.DTOS.consumo.ConsumoResponseDTO;
import com.FIAP.EnergyWise.services.ConsumoService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/consumo")
@Tag(name = "Consumo", description = "API de consumo de energia. Representa o consumo de energia de uma comunidade para que seja possível calcular a quantidade de placas solares necessárias.")
public class ConsumoController {

    @Autowired
    private ConsumoService consumoService;

    @Operation(summary = "Cria um consumo de energia para uma comunidade, passando apenas o valor do consumo. A data do consumo é gerada automaticamente no momento da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consumo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    @PostMapping({"/{idComunidade}"})
    public ResponseEntity<ConsumoResponseDTO> createConsumo(@RequestBody
                                                            ConsumoRequestDTO consumoRequestDTO, Long idComunidade) {
        if (idComunidade == null) {
            return ResponseEntity.status(400).build();
        }
        ConsumoResponseDTO consumo = consumoService.createConsumo(consumoRequestDTO, idComunidade);
        return ResponseEntity.status(201).body(consumo);
    }

    @Operation(summary = "Busca todos os consumos de energia paginados e ordenados de forma decrescente pela data de consumo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consumos encontrados"),
            @ApiResponse(responseCode = "404", description = "Consumos não encontrados")
    })
    @GetMapping
    public ResponseEntity<List<EntityModel<ConsumoResponseDTO>>> findAllConsumos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ConsumoResponseDTO>
                consumos = consumoService.findAllConsumos(page, size);

        if (consumos.isEmpty()) {
            return ResponseEntity.status(404).build();
        }


        return ResponseEntity.status(HttpStatus.OK).body(consumos.stream()
                .map(consumo -> EntityModel.of(consumo, linkTo(methodOn(ConsumoController.class)
                        .findAllConsumos(page, size)).withSelfRel())).toList());
    }

    @Operation(summary = "Busca os consumos de uma comunidade paginados e ordenados de forma decrescente pela data de consumo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consumo encontrado"),
            @ApiResponse(responseCode = "404", description = "Consumo não encontrado")
    })
    @GetMapping("/{idComunidade}")
    public ResponseEntity<List<EntityModel<ConsumoResponseDTO>>> findConsumoByComunidade(
            @PathVariable Long idComunidade,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ConsumoResponseDTO> consumos = consumoService.findConsumoByComunidade(idComunidade, page, size);
        if (consumos.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(consumos.stream()
                .map(consumo -> EntityModel.of(consumo, linkTo(methodOn(ConsumoController.class)
                        .findConsumoByComunidade(idComunidade, page, size)).withSelfRel())).toList());
    }


}
