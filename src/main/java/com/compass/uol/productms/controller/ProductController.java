package com.compass.uol.productms.controller;

import com.compass.uol.productms.entity.Product;
import com.compass.uol.productms.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/products", produces = "application/json")
public class ProductController {

    @Autowired
    ProductService productService;

    @ApiOperation(value = "Endpoint para criação de um novo produto.")
    @PostMapping()
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
            return productService.createNewProduct(product);
    }

    @ApiOperation(value = "Endpoint para atualizacao de um produto.")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable(required = true) Integer id, @RequestBody @Valid Product product) {
        Optional<Product> updateProduct = productService.updateProduct(id, product);
        return updateProduct.isPresent()
                ? new ResponseEntity<>(updateProduct.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Endpoint para receber um produto.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(
            @PathVariable(required = true) Integer id) {
        Optional<Product> checkProduct = productService.checkProduct(id);
        return checkProduct.isPresent()
                ? new ResponseEntity<>(checkProduct.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Endpoint para receber todos os produtos.")
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @ApiOperation(value = "Endpoint para deletar um produto.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable(required = true) Integer id) {
        Optional<Product> checkProduct = productService.deleteProduct(id);
        return checkProduct.isPresent()
                ? new ResponseEntity<>(checkProduct.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Endpoint para buscar um produto")
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) BigDecimal min,
            @RequestParam(required = false) BigDecimal max
    ) {
        return new ResponseEntity<>(productService.find(text, min, max), HttpStatus.OK);

    }

}
