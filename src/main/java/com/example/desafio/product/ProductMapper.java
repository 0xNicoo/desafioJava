package com.example.desafio.product;

import com.example.desafio.category.Category;

import java.math.BigDecimal;


public class ProductMapper {

    public static Product createProduct(ProductDto productDto, Category category){
        Product product = new Product();
        if (productDto.getName() == null || productDto.getName().length() == 0){
            throw new IllegalStateException("Invalid product name");
        }
        if (productDto.getPrice() == null || productDto.getPrice().compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalStateException("Invalid price");
        }
        if (productDto.getStock() == null){
            throw new IllegalStateException("Invalid stock");
        }
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setCategory(category);

        return product;
    }
}
