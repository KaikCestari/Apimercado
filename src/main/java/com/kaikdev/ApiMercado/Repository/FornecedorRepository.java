package com.kaikdev.ApiMercado.Repository;

import com.kaikdev.ApiMercado.Model.Entity.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}
