package com.nphase.service;

import com.nphase.entity.CategoryAggregationDto;

import java.math.BigDecimal;
import java.util.Objects;

public class DiscountService {

    private static final int DISCOUNT_COUNT = Integer.parseInt(Objects.requireNonNullElse(
            System.getenv("DISCOUNT_COUNT"), "3"));
    private static final BigDecimal DISCOUNT_FRACTION = new BigDecimal(Objects.requireNonNullElse(
            System.getenv("DISCOUNT_FRACTION"), "0.1"));

    public BigDecimal checkAndApplyDiscount(CategoryAggregationDto aggregationDto) {
        if (aggregationDto.getCount() > DISCOUNT_COUNT) {
            return aggregationDto.getTotalPrice().multiply(BigDecimal.ONE.subtract(DISCOUNT_FRACTION));
        }
        return aggregationDto.getTotalPrice();
    }

}
