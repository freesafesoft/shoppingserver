package com.fss.shopping.persistence.dao;

import com.fss.shopping.persistence.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketRepository extends JpaRepository<Market, Long> {
    public List<Market> findTop10ByNameStartingWithOrderByNameDesc(String name);

    public List<Market> findTop100ByAddressStartingWithOrderByName(String address);
}
