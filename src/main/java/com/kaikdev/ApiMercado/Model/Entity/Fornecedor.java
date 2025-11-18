package com.kaikdev.ApiMercado.Model.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "fornecedor")
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

    public Fornecedor(String cnpj, String name, boolean ativo, LocalDateTime createdAt) {
        this.cnpj = cnpj;
        this.name = name;
        this.ativo = ativo;
        this.createdAt = createdAt;
    }
}
