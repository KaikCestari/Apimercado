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

}
