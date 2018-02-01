package com.fss.shopping.persistence.dao;

import com.fss.shopping.persistence.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {
    public List<Price> findPricesByPrimaryKeyIs(long marketId);
}
