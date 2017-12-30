package com.fss.shopping.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AssociationOverrides({@AssociationOverride(name = "primaryKey.market", joinColumns = @JoinColumn(name = "market_id")),
        @AssociationOverride(name = "primaryKey.product", joinColumns = @JoinColumn(name = "product_id"))})
public class Price {
    @EmbeddedId
    private PriceId primaryKey = new PriceId();
    private float defaultPrice;
    private float discountPrice;
    private float cardPrice;

    public PriceId getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(PriceId primaryKey) {
        this.primaryKey = primaryKey;
    }

    public float getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(float defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public float getCardPrice() {
        return cardPrice;
    }

    public void setCardPrice(float cardPrice) {
        this.cardPrice = cardPrice;
    }

    @Embeddable
    public static class PriceId implements Serializable {
        @ManyToOne(cascade = CascadeType.ALL)
        private Market market;

        @ManyToOne(cascade = CascadeType.ALL)
        private Product product;

        public Market getMarket() {
            return market;
        }

        public void setMarket(Market market) {
            this.market = market;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }
    }
}
