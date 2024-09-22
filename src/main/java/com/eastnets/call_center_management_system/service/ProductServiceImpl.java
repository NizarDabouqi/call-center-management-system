package com.eastnets.call_center_management_system.service;

import com.eastnets.call_center_management_system.model.Product;
import com.eastnets.call_center_management_system.repository.ProductRepository;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product find() {
        return productRepository.find();
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
