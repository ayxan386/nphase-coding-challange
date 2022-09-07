package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;

public class ShoppingCartService {

    private static final int DISCOUNT_COUNT = 3;
    private static final BigDecimal DISCOUNT_FRACTION = BigDecimal.valueOf(0.1);

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts()
                .stream()
                .map(this::calculatePrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal calculatePrice(Product product) {
        final var res = product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()));
        if (product.getQuantity() > DISCOUNT_COUNT) {
            return res.multiply(BigDecimal.ONE.subtract(DISCOUNT_FRACTION));
        }
        return res;
    }
}
