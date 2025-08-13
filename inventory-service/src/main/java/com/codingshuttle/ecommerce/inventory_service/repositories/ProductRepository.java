package com.codingshuttle.ecommerce.inventory_service.repositories;

import com.codingshuttle.ecommerce.inventory_service.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
