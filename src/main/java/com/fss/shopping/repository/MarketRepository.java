package com.fss.shopping.repository;

import com.fss.shopping.entity.Market;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MarketRepository extends CrudRepository<Market, Long> {
    public List<Market> findTop10ByNameStartingWithOrderByNameDesc(String name);

    public List<Market> findTop100ByAddressStartingWithOrderByName(String address);
}
