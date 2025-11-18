package com.kaikdev.ApiMercado.Model.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "product")
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

    public Product(LocalDateTime createdAt, Integer estoque_atual, String unidade, String categoria, String name) {
        this.createdAt = createdAt;
        this.estoque_atual = estoque_atual;
        this.unidade = unidade;
        this.categoria = categoria;
        this.name = name;
    }

    @Column(nullable = false)
    private Integer estoque_atual;
    @CreationTimestamp
    private LocalDateTime createdAt;




}
