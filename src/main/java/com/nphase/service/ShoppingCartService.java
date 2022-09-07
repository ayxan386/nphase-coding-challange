package com.nphase.service;

import com.nphase.entity.CategoryAggregationDto;
import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.util.HashMap;

public class ShoppingCartService {

    private static final int DISCOUNT_COUNT = 3;
    private static final BigDecimal DISCOUNT_FRACTION = BigDecimal.valueOf(0.1);

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        final var groupedProducts = groupByCategories(shoppingCart);
        return groupedProducts.values()
                .stream()
                .map(this::checkAndApplyDiscount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private HashMap<String, CategoryAggregationDto> groupByCategories(ShoppingCart shoppingCart) {
        HashMap<String, CategoryAggregationDto> groupedProducts = new HashMap<>();
        shoppingCart.getProducts().forEach(product -> {
            groupedProducts.computeIfAbsent(product.getCategory(), key -> new CategoryAggregationDto());
            groupedProducts.computeIfPresent(product.getCategory(), (key, agg) -> aggregateProduct(product, agg));
        });
        return groupedProducts;
    }

    private CategoryAggregationDto aggregateProduct(Product product, CategoryAggregationDto agg) {
        agg.increaseCount(product.getQuantity());
        agg.calculateNewTotal(calculatePrice(product));
        return agg;
    }

    private BigDecimal calculatePrice(Product product) {
        return product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()));
    }

    private BigDecimal checkAndApplyDiscount(CategoryAggregationDto aggregationDto) {
        if (aggregationDto.getCount() > DISCOUNT_COUNT) {
            return aggregationDto.getTotalPrice().multiply(BigDecimal.ONE.subtract(DISCOUNT_FRACTION));
        }
        return aggregationDto.getTotalPrice();
    }

}
