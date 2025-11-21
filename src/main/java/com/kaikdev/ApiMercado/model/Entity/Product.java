package com.kaikdev.ApiMercado.model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@Table(name = "product")
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
   private String categoria;
    @Column(nullable = false)
    private String unidade;

    public Product(LocalDateTime createdAt, Integer quantidadeAtual, String unidade, String categoria, String name, Integer capacidadeMaxima) {
        this.createdAt = createdAt;
        this.quantidadeAtual = quantidadeAtual;
        this.unidade = unidade;
        this.categoria = categoria;
        this.name = name;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    @Column(name = "estoque_atual", nullable = false)
    private Integer quantidadeAtual;

    @Column(nullable = false)
    private Integer capacidadeMaxima;
    @CreationTimestamp
    private LocalDateTime createdAt;




}
