package com.example.desafio.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProduct(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String stock,
            @RequestParam(required = false) String category
    ){
        return productService.getAllProducts(name, price, stock, category);
    }

    @GetMapping(path = "/{productId}")
    public Product getProduct(@PathVariable Integer productId){ return productService.getProduct(productId);}

    @PostMapping
    public void createProduct(@RequestBody ProductDto productDto){
        productService.createProduct(productDto);
    }

    @DeleteMapping(path = "/{productId}")
    public void deleteProduct(@PathVariable("productId") Integer productId){productService.deleteProduct(productId);}

    @PutMapping(path = "/{productId}")
    public void updateProduct(
            @PathVariable("productId") Integer productId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String stock,
            @RequestParam(required = false) String categoryId
    ){
        productService.updateProduct(productId, name, price, stock, categoryId);
    }
}
