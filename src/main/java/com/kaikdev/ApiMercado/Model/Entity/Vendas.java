package com.kaikdev.ApiMercado.Model.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendas")
@Data
@NoArgsConstructor
public class Vendas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @Column(nullable = false)
    private Integer quantidade;
    @Column(nullable = false)
    private Double preco_venda;
    @Column(nullable = false)
    private Double preco_compra;
    @Column(nullable = false)
    private Double margem_lucro;



}
