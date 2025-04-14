package com.FIAP.EnergyWise.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {

    private Date timestamp;
    private String mensagem;
    private int status;





}
