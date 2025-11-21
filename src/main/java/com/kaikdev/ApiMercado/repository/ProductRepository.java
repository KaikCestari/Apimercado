package com.kaikdev.ApiMercado.repository;

import com.kaikdev.ApiMercado.model.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByName(String name);
}
