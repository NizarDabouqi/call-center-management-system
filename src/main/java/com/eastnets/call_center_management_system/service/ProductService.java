package com.eastnets.call_center_management_system.service;

import com.eastnets.call_center_management_system.model.Product;

import java.util.List;

public interface ProductService {

    Product find();

    List<Product> findAll();
}
