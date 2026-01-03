package com.kaikdev.Tera.model.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "gastos")
@Data
@Builder
public class Gastos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private LocalDateTime creationStamp;

    @Column(unique = true)
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "tb_categoria")
    private Categoria categoria;




}
