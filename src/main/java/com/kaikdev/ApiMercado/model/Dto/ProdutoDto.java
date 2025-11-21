package com.kaikdev.ApiMercado.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDto {


    private String name;
    private String categoria;
    private String unidade;
    private Integer quantidadeAtual;
    private Integer capacidadeMaxima;





}
