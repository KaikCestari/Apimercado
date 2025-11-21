package com.kaikdev.ApiMercado.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FornecedorDto {


    private String cnpj;
    private String name;
    private boolean ativo = true;
    private LocalDateTime createdAt;
    private String reputacao;
}
