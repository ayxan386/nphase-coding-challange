package com.nphase.service;


import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

class ShoppingCartServiceTest {
    private final ShoppingCartService service = new ShoppingCartService();

    //This could be replaced by parameterized tests if new dependency is added
    @Test
    void whenDiscountDoesNotApply() {
        BigDecimal expectedResult = BigDecimal.valueOf(32.5);

        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 1, "tea"),
                new Product("Chair", BigDecimal.valueOf(17.0), 1, "furniture"),
                new Product("Coffee", BigDecimal.valueOf(3.5), 3, "coffee")
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(0, expectedResult.compareTo(result));
    }

    @Test
    void whenDiscountAppliedToSingleItem() {
        BigDecimal expectedResult = BigDecimal.valueOf(50.0);

        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 5, "tea"),
                new Product("Chair", BigDecimal.valueOf(17.0), 1, "furniture"),
                new Product("Coffee", BigDecimal.valueOf(3.5), 3, "coffee")
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(0, expectedResult.compareTo(result));
    }

    @Test
    void whenDiscountAppliedToWholeCategory() {
        BigDecimal expectedResult = BigDecimal.valueOf(48.95);

        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 5, "drink"),
                new Product("Chair", BigDecimal.valueOf(17.0), 1, "furniture"),
                new Product("Coffee", BigDecimal.valueOf(3.5), 3, "drink")
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(0, expectedResult.compareTo(result));
    }

    @Test
    void whenDiscountAppliedToSeveralCategories() {
        BigDecimal expectedResult = BigDecimal.valueOf(93.15);

        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 5, "drink"),
                new Product("Chair", BigDecimal.valueOf(17.0), 4, "furniture"),
                new Product("Coffee", BigDecimal.valueOf(3.5), 3, "drink")
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(0, expectedResult.compareTo(result));

    }

}
