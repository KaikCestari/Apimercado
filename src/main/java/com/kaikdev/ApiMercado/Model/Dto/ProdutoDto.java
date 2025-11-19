package com.kaikdev.ApiMercado.Model.Dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDto {


    private String name;
    private String categoria;
    private String unidade;
    private Integer estoque_atual;
    private String createdAt;





}
