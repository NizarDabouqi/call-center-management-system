package com.eastnets.call_center_management_system.repository;

import com.eastnets.call_center_management_system.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {


    @Override
    public Product find() {
        return new Product("p01", "name 1",100 );
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<Product>();
        products.add(new Product("p01", "name 1", 100));
        products.add(new Product("p02", "name 2", 200));
        products.add(new Product("p03", "name 3", 300));
        return products;
    }
}
