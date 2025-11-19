package com.kaikdev.ApiMercado.Model.Entity;

import com.kaikdev.ApiMercado.Model.Enum.ReputacaoFornecedor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "fornecedor")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String cnpj;
    @Column(unique = true)
    private String name;
    @Column(nullable = false)
    private boolean ativo = true;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private ReputacaoFornecedor reputacaoFornecedor;

    public Fornecedor(String cnpj, String name, boolean ativo, LocalDateTime createdAt, ReputacaoFornecedor reputacaoFornecedor) {
        this.cnpj = cnpj;
        this.name = name;
        this.ativo = ativo;
        this.createdAt = createdAt;
        this.reputacaoFornecedor = reputacaoFornecedor;
    }
}
