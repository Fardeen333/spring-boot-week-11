package com.codingshuttle.ecommerce.inventory_service.service;

import com.codingshuttle.ecommerce.inventory_service.dto.OrderRequestDto;
import com.codingshuttle.ecommerce.inventory_service.dto.OrderRequestItemDto;
import com.codingshuttle.ecommerce.inventory_service.dto.ProductDto;
import com.codingshuttle.ecommerce.inventory_service.entities.ProductEntity;
import com.codingshuttle.ecommerce.inventory_service.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllInventory() {
        log.info("Fetching all the inventory items");
        List<ProductEntity> inventories = productRepository.findAll();
        return inventories.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();

    }

    public ProductDto getProductById(Long id) {
        log.info("Fetching product with id : {}", id);
        Optional<ProductEntity> inventory = productRepository.findById(id);
        return inventory
                .map(item -> modelMapper.map(item, ProductDto.class))
                .orElseThrow(() -> new RuntimeException("Inventory not found."));
    }

    @Transactional
    public Double reduceStocks(OrderRequestDto orderRequestDto) {
        Double totalPrice = 0.0;
        log.info("Reducing the stocks");
        for (OrderRequestItemDto orderRequestItemDto : orderRequestDto.getItems()) {
            Long productId = orderRequestItemDto.getProductId();
            Integer quantity = orderRequestItemDto.getQuantity();

            ProductEntity product = productRepository.findById(productId).orElseThrow(() ->
                    new RuntimeException("Product not found with id: " + productId));
            if (product.getStock() < quantity) {
                throw new RuntimeException("Product cannot be fulfilled for given quantity");
            }

            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
            totalPrice += quantity * product.getPrice();
        }
        return totalPrice;
    }
}
