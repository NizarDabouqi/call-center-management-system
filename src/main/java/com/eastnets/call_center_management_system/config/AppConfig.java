package com.eastnets.call_center_management_system.config;

import com.eastnets.call_center_management_system.repository.ProductRepository;
import com.eastnets.call_center_management_system.repository.ProductRepositoryImpl;
import com.eastnets.call_center_management_system.service.ProductService;
import com.eastnets.call_center_management_system.service.ProductServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.eastnets")
public class AppConfig {

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepositoryImpl();
    }

    @Bean
    public ProductService productService(ProductRepository productRepository) {
        return new ProductServiceImpl(productRepository);
    }


}
