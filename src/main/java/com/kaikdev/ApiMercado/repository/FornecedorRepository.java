package com.kaikdev.ApiMercado.repository;

import com.kaikdev.ApiMercado.model.Entity.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    boolean existsByCnpj(String cnpj);
}
