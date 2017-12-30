package com.fss.shopping.repository;

import com.fss.shopping.entity.Price;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PriceRepository extends CrudRepository<Price, Long> {
    public List<Price> findPricesByPrimaryKeyIs(long marketId);
}
