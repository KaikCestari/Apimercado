package com.kaikdev.ApiMercado.repository;

import com.kaikdev.ApiMercado.model.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueMovimentoRepository extends JpaRepository<EstoqueMovimento, Long> {

    Optional<Product> findByProductId(Long productId);
    Boolean validarQuantidadeEstoque( Integer quantidade);
    Boolean validarValor( Double valor);
}
