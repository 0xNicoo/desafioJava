package com.example.desafio.service;

import com.example.desafio.model.Category;
import com.example.desafio.repository.CategoryRepository;
import com.example.desafio.exception.ApiRequestException;
import com.example.desafio.model.Product;
import com.example.desafio.product.ProductDto;
import com.example.desafio.product.ProductMapper;
import com.example.desafio.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProducts(String name, String price, String stock, String category){
        List<Product> productList = productRepository.findAll();

        if(name == null && price == null && stock == null && category == null){
            return productList;
        }else{
            List<Product> auxList = new ArrayList<Product>(productList);

            productList.forEach(p ->{
                if(name != null && !Objects.equals(p.getName(), name)){
                    auxList.remove(p);
                }
                if(price != null && p.getPrice().compareTo(new BigDecimal(price)) != 0){
                    auxList.remove(p);
                }
                if(stock != null && p.getStock() != Integer.parseInt(stock)){
                    auxList.remove(p);
                }
                if(category != null && !Objects.equals(p.getCategory(), category)){
                    auxList.remove(p);
                }
            });

            return auxList;
        }
    }

    public Product getProduct(Integer productId){
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new ApiRequestException("Product with id: "+ productId+ " don't exist"));
    }

    public void createProduct(ProductDto productDto){
        Category category = categoryRepository
                .findById(productDto.getCategoryId())
                .orElseThrow(() -> new ApiRequestException("Invalid category"));
        Product product = ProductMapper.createProduct(productDto, category);
        productRepository.save(product);
    }

    public void deleteProduct(Integer productId){
        boolean exists = productRepository.existsById(productId);
        if(!exists){
            throw new ApiRequestException("Product with id: "+ productId +" don't exist");
        }
        productRepository.deleteById(productId);
    }

    @Transactional
    public void  updateProduct(Integer productId, String name, String price, String stock, String categoryId){
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ApiRequestException("Product with id " + productId +" don't exist"));

        if(categoryId != null){
            Category category = categoryRepository
                    .findById(Integer.parseInt(categoryId))
                    .orElseThrow(() -> new ApiRequestException("Category with id " + categoryId + " don't exist"));
            if(!Objects.equals(category.getId(), categoryId)){
                product.setCategory(category);
            }
        }

        if (name != null && name.length() > 0){
            product.setName(name);
        }
        if (price != null){
            product.setPrice(new BigDecimal(price));
        }
        if (stock != null && Integer.parseInt(stock) >= 0){
            product.setStock(Integer.parseInt(stock));
        }

    }
}
