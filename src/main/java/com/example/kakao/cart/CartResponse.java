package com.example.kakao.cart;

import com.example.kakao.option.Option;
import com.example.kakao.option.OptionResponse;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CartResponse {

    private int cartId;
    private int optionId;
    private String optionName;
    private int quantity;
    private int price;
    private Option option;
    private int totalPrice;

    public static CartResponse of(Cart cart, Option option){
        return CartResponse.builder()
                .cartId(cart.getId())
                .option(option)
                .quantity(cart.getQuantity())
                .price(cart.getPrice())
                .build();
    }
}
