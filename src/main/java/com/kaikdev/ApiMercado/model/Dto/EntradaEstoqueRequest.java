package com.kaikdev.ApiMercado.model.Dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EntradaEstoqueRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Long fornecedorId;

    @NotNull
    @Min(1)
    private Integer quantidade;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Double precoCompra;
}
