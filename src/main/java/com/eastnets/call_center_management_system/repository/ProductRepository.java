package com.eastnets.call_center_management_system.repository;

import com.eastnets.call_center_management_system.model.Product;

import java.util.List;

public interface ProductRepository {

    Product find();

    List<Product> findAll();
}
