package dev.tiagomarks.java_demo.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.tiagomarks.java_demo.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
