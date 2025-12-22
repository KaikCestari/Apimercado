package com.kaikdev.ApiMercado.model.Entity;

import com.kaikdev.ApiMercado.model.Enum.TipoMovimentoEstoque;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "estoque_movimento")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueMovimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @Column(nullable = false)
    private Integer quantidade;

    private Double precoCompra;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoMovimentoEstoque tipo;

    private String motivo;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
