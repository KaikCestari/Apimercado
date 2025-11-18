package com.kaikdev.ApiMercado.Repository;

import com.kaikdev.ApiMercado.Model.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
