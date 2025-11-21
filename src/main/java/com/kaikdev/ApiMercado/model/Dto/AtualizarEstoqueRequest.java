package com.kaikdev.ApiMercado.model.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarEstoqueRequest {
    @NotNull(message = "Quantidade atual é obrigatória")
    @Min(value = 0, message = "Quantidade deve ser positiva")
    private Integer quantidadeAtual;
}
