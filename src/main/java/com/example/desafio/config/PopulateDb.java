package com.example.desafio.config;

import com.example.desafio.model.Category;
import com.example.desafio.repository.CategoryRepository;
import com.example.desafio.model.Product;
import com.example.desafio.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Configuration
public class PopulateDb {
    @Bean
    CommandLineRunner commandLineRunner(CategoryRepository categoryRepository, ProductRepository productRepository){
        return args -> {
            Category limpieza = new Category("Limpieza", Collections.emptySet());
            Category cocina = new Category("Cocina", Collections.emptySet());
            categoryRepository.saveAllAndFlush(List.of(limpieza,cocina));
            Optional<Category> c = categoryRepository.findCategoryByName("Cocina");
            Optional<Category> l = categoryRepository.findCategoryByName("Limpieza");
            Product product = new Product("Aceite", new BigDecimal(200), 23, c.get());
            Product product2 = new Product("Mayonesa", new BigDecimal(200), 55, c.get());
            Product product3 = new Product("Lavandina", new BigDecimal(300), 23, l.get());
            Product product4 = new Product("Escoba", new BigDecimal(200), 55, l.get());
            productRepository.saveAll(List.of(product,product2,product3,product4));
        };
    }
}
