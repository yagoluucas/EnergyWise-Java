package com.FIAP.EnergyWise.DTOS.consumo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ConsumoRequestDTO {


    @NotNull
    private BigDecimal energiaConsumida;
}
