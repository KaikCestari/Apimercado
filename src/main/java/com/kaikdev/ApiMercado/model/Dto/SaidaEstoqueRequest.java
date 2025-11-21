package com.kaikdev.ApiMercado.model.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaidaEstoqueRequest {

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Integer quantidade;

    private String motivo;
}
