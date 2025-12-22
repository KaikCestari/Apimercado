package com.kaikdev.ApiMercado.model.Dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EstoqueProdutoResponse {
    Long productId;
    String nome;
    String categoria;
    String unidade;
    Integer quantidadeAtual;
    Integer capacidadeMaxima;
}
