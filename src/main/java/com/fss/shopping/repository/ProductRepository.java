package com.fss.shopping.repository;

import com.fss.shopping.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
