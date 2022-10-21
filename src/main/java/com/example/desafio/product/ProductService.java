package com.example.desafio.product;

import com.example.desafio.category.Category;
import com.example.desafio.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProduct(Integer productId){
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new IllegalStateException("Product with id: "+ productId+ " don't exist"));
    }

    public void createProduct(ProductDto productDto){
        Optional<Category> category = categoryRepository.findById(productDto.getCategoryId());
        if(category.isEmpty()){
            throw new IllegalStateException("Category with id "+ productDto.getCategoryId() +" don't exist");
        }
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setCategory(category.get());
        productRepository.save(product);
    }

    public void deleteProduct(Integer productId){
        boolean exists = productRepository.existsById(productId);
        if(!exists){
            throw new IllegalStateException("Product with id: "+ productId +" don't exist");
        }
        productRepository.deleteById(productId);
    }

    @Transactional
    public void  updateProduct(Integer productId, String name, String price, String stock, String categoryId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Product with id " + productId +"don't exist"));
        Category category = categoryRepository.findById(Integer.parseInt(categoryId)).orElseThrow(() -> new IllegalStateException("Category with id " + categoryId + "don't exist"));
        if (name != null && name.length() > 0 && !Objects.equals(product.getName(), name)){
            product.setName(name);
        }
        if (price != null && !Objects.equals(product.getPrice(), price)){
            product.setPrice(new BigDecimal(price));
        }
        if (stock != null && Integer.parseInt(stock) >= 0 && !Objects.equals(product.getStock(), stock)){
            product.setStock(Integer.parseInt(stock));
        }
        if(!Objects.equals(category.getId(), categoryId)){
            product.setCategory(category);
        }
    }
}
