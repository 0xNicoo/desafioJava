package com.example.desafio.config;

import com.example.desafio.category.Category;
import com.example.desafio.category.CategoryRepository;
import com.example.desafio.product.Product;
import com.example.desafio.product.ProductRepository;
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
            Product product = new Product("Aceite", new BigDecimal(200), 23, c.get());
            productRepository.save(product);
        };
    }
}
