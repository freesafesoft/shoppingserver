package com.fss.shopping.controller;

import com.fss.shopping.entity.Market;
import com.fss.shopping.entity.Price;
import com.fss.shopping.entity.Product;
import com.fss.shopping.repository.MarketRepository;
import com.fss.shopping.repository.PriceRepository;
import com.fss.shopping.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CreateController {
    private final MarketRepository marketRepository;
    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;

    public CreateController(MarketRepository marketRepository, ProductRepository productRepository, PriceRepository priceRepository) {
        System.out.println("CreateController");
        this.priceRepository = priceRepository;
        this.marketRepository = marketRepository;
        this.productRepository = productRepository;
    }

    @RequestMapping("/createmarkets")
    public ModelAndView createMarkets() {
        System.out.println("markets");
        ArrayList<Market> markets = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Market market = new Market();
            market.setName("name" + i);
            market.setAddress("address" + i);
            market.setLocation("Томск");
            market.setType("Продуктовый");
            market.setLatitude(13.33f);
            market.setLongitude(129.4754f);
            markets.add(market);
        }

        marketRepository.save(markets);
        Map<String, String> model = new HashMap<>();
        model.put("name", "markets");
        model.put("count", "100");
        return new ModelAndView("create", model);
    }

    @RequestMapping("/createproducts")
    public ModelAndView createProducts() {
        ArrayList<Product> products = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setName("name" + i);
            product.setCategory("category" + i);
            product.setProducer("producer" + i);
            products.add(product);
        }

        productRepository.save(products);
        Map<String, String> model = new HashMap<>();
        model.put("name", "products");
        model.put("count", "100");
        return new ModelAndView("create", model);
    }

    @RequestMapping("/createprices")
    public ModelAndView createPrices() {
        Iterable<Market> markets = marketRepository.findAll();
        Iterable<Product> products = productRepository.findAll();
        int count = 0;
        int priceIndex = 1;
        ArrayList<Price> prices = new ArrayList<>();
        for (Market market : markets) {

            for (Product product : products) {
                Price price = new Price();
                Price.PriceId id = new Price.PriceId();
                id.setMarket(market);
                id.setProduct(product);
                price.setPrimaryKey(id);
                price.setDefaultPrice(priceIndex * 10.5f);
                price.setCardPrice(price.getDefaultPrice() * 0.9f);
                price.setDiscountPrice(price.getDefaultPrice() * 0.75f);
                prices.add(price);
                count++;
                priceIndex++;
            }
        }
        priceRepository.save(prices);
        Map<String, String> model = new HashMap<>();
        model.put("name", "prices");
        model.put("count", "" + count);
        return new ModelAndView("create", model);
    }

    @RequestMapping("/findmarket")
    public ModelAndView findMarkets() {
        List<Market> markets = marketRepository.findTop10ByNameStartingWithOrderByNameDesc("name");
        Map<String, String> model = new HashMap<>();
        model.put("name", markets.get(0).getName());
        model.put("count", "" + markets.size());
        return new ModelAndView("create", model);
    }
}
