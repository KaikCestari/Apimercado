package com.kaikdev.ApiMercado.Model.Entity;

import com.kaikdev.ApiMercado.Model.Enum.ReputacaoFornecedor;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "fornecedor")
@Data
@NoArgsConstructor
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String cnpj;
    @Column(unique = true)
    private String name;
    @Column(nullable = false)
    private boolean ativo;
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
