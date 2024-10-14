package com.bar.domain.shared;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.bar.exception.NegativePriceException;
import com.bar.exception.IllegalDecimalException;

import jakarta.persistence.Embeddable;

@Embeddable
public record Price(double value) {

    public Price {

        if (value < 0) {

            throw new NegativePriceException("Price can not be negative");
        }

        BigDecimal priceValue = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);

        if (priceValue.scale() > 2) {

            throw new IllegalDecimalException("Price cannot have more than two decimal places");
        }

        value = priceValue.doubleValue();
    }
}