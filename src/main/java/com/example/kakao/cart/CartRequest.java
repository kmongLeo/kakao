package com.example.kakao.cart;

import com.example.kakao.option.Option;
import com.example.kakao.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class CartRequest {

    private int optionId;
    private int quantity;
    private int userId;
    private int cartId;

    public Cart toCartEntity(Option option, User user){
        return Cart.builder()
                .option(option)
                .user(user)
                .price(option.getPrice())
                .quantity(quantity)
                .build();
    }
}
