package com.kaikdev.ApiMercado.Model.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    @Column(nullable = false)
    private BigDecimal price;


}
