package com.kaikdev.ApiMercado.model.Entity;

import com.kaikdev.ApiMercado.model.Enum.ReputacaoFornecedor;
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
    @Builder.Default
    @Column(nullable = false)
    private Double margemMedia = 0.0;
    @Builder.Default
    @Column(nullable = false)
    private Integer totalVendas = 0;
    @Builder.Default
    @Column(nullable = false)
    private Integer entregasRealizadas = 0;
    @Builder.Default
    @Column(nullable = false)
    private Double custoBeneficio = 0.0;
    @Builder.Default
    @Column(nullable = false)
    private Double desempenhoHistorico = 0.0;
    @Builder.Default
    @Column(nullable = false)
    private Double precoMedioCompra = 0.0;
    @Builder.Default
    @Column(nullable = false)
    private Double precoMedioVenda = 0.0;

    public Fornecedor(String cnpj, String name, boolean ativo, LocalDateTime createdAt, ReputacaoFornecedor reputacaoFornecedor) {
        this.cnpj = cnpj;
        this.name = name;
        this.ativo = ativo;
        this.createdAt = createdAt;
        this.reputacaoFornecedor = reputacaoFornecedor;
        this.margemMedia = 0.0;
        this.totalVendas = 0;
        this.entregasRealizadas = 0;
        this.custoBeneficio = 0.0;
        this.desempenhoHistorico = 0.0;
        this.precoMedioCompra = 0.0;
        this.precoMedioVenda = 0.0;
    }
}
