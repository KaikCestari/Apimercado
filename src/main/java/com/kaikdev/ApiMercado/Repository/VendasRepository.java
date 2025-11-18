package com.kaikdev.ApiMercado.Repository;

import com.kaikdev.ApiMercado.Model.Entity.Vendas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendasRepository extends JpaRepository<Vendas, Long> {
}
