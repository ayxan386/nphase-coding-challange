package com.nphase.service;

import com.nphase.entity.CategoryAggregationDto;
import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.util.HashMap;

public class ShoppingCartService {

    private final DiscountService discountService = new DiscountService();

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        final var groupedProducts = groupByCategories(shoppingCart);
        return groupedProducts.values()
                .stream()
                .map(discountService::checkAndApplyDiscount)
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

}
