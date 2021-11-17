package com.compass.uol.productms.repository;

import com.compass.uol.productms.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query("SELECT p FROM Product p " +
            "WHERE p.name LIKE :text " +
            "OR p.description LIKE :text " +
            "OR ((:min IS NULL OR p.price >= :min) " +
            "AND (:max IS NULL OR p.price <= :max))"
            )
            List<Product> find(String text, BigDecimal min, BigDecimal max);
}
