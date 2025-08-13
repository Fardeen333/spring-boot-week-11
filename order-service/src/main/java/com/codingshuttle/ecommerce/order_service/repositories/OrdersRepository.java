package com.codingshuttle.ecommerce.order_service.repositories;

import com.codingshuttle.ecommerce.order_service.entities.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {
}
