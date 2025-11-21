package com.kaikdev.ApiMercado.model.Dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class VendaResponse {
    Long id;
    String produto;
    String fornecedor;
    Integer quantidade;
    Double precoVenda;
    Double precoCompra;
    Double margemLucro;
    LocalDateTime createdAt;
}
