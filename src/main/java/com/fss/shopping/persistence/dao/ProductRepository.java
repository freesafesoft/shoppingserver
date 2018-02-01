package com.fss.shopping.persistence.dao;

import com.fss.shopping.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Product Data Access Object
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
