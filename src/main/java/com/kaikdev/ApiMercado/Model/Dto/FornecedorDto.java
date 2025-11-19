package com.kaikdev.ApiMercado.Model.Dto;

import com.kaikdev.ApiMercado.Model.Enum.ReputacaoFornecedor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
