package com.eastnets.call_center_management_system.controller;

import com.eastnets.call_center_management_system.model.Product;
import com.eastnets.call_center_management_system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@Component
@ManagedBean(name = "ProductManagedBean")
@SessionScoped
public class ProductManagedBean {

    @Autowired
    public ProductService productService;

    private Product product;

    private List<Product> products;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void onload() {
        this.product = productService.find();
        this.products = productService.findAll();
    }
}
