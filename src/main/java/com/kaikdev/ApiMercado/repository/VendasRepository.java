package com.kaikdev.ApiMercado.repository;

import com.kaikdev.ApiMercado.model.Entity.Vendas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendasRepository extends JpaRepository<Vendas, Long> {
}
