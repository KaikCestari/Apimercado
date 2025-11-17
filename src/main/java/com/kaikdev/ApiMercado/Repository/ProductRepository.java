package com.kaikdev.ApiMercado.Repository;

import com.kaikdev.ApiMercado.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
