package com.compass.uol.productms.service;

import com.compass.uol.productms.entity.Product;
import com.compass.uol.productms.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<Product> createNewProduct(Product product){
        Product productCreated = productRepository.save(product);
        return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
    }

    @Transactional
    public Optional<Product> updateProduct(Integer id, Product product){
        Optional<Product> checkProductExists = checkProduct(id);
        if (checkProductExists.isPresent()) {
            product.setId(id);
            if (product.getName().isEmpty()) {
                product.setName(checkProductExists.get().getName());
            }
            if (product.getDescription().isEmpty()) {
                product.setDescription(checkProductExists.get().getDescription());
            }
            if (product.getPrice() == null) {
                product.setPrice(checkProductExists.get().getPrice());
            }
            productRepository.save(product);
        }
        return checkProductExists;
    }

    @Transactional
    public Optional<Product> deleteProduct(Integer id){
        Optional<Product> checkProductExists = checkProduct(id);
        if (checkProductExists.isPresent()) {
            productRepository.delete(checkProductExists.get());
        }
        return checkProductExists;
    }

    public Optional<Product> checkProduct(int id){
        Optional<Product> product = productRepository.findById(id);
        return product;
    }

    public List<Product> getAllProducts(){
        return (List<Product>) productRepository.findAll();
    }

    public List<Product> find(String text, BigDecimal min, BigDecimal max){
        return productRepository.find(text, min, max);
    }
}
