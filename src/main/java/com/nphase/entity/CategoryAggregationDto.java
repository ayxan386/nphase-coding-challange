package com.nphase.entity;

import java.math.BigDecimal;

public class CategoryAggregationDto {
    private int count;
    private BigDecimal totalPrice;

    public CategoryAggregationDto() {
        this.count = 0;
        this.totalPrice = BigDecimal.ZERO;
    }

    public int getCount() {
        return count;
    }

    public void increaseCount(int count) {
        this.count += count;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void calculateNewTotal(BigDecimal toAdd) {
        this.totalPrice = totalPrice.add(toAdd);
    }


}
