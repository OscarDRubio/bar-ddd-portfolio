package com.bar.domain.shared;

import java.math.BigDecimal;

import com.bar.domain.exception.IllegalDecimalException;
import com.bar.domain.exception.NegativePriceException;

import jakarta.persistence.Embeddable;

@Embeddable
public record Price(double price) {

    public Price {

        if (price < 0) {

            throw new NegativePriceException("Price can not be negative");
        }

        BigDecimal priceValue = BigDecimal.valueOf(price);

        if (priceValue.scale() > 2) {

            throw new IllegalDecimalException("Price cannot have more than two decimal places");
        }

        price = priceValue.doubleValue();
    }
    
    public double asDouble() {
        return price;
    }
}