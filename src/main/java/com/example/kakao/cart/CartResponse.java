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
@Setter
public class CartResponse {

    private int cartId;
    private int optionId;
    private String optionName;
    private int quantity;
    private int price;

    public static CartResponse of(Cart cart){
        return CartResponse.builder()
                .cartId(cart.getId())
                .optionId(cart.getOption().getId())
                .optionName(cart.getOption().getOptionName())
                .quantity(cart.getQuantity())
                .price(cart.getPrice())
                .build();

    }

    @Getter
    public static class CartUpdateResponse{
        private int totalPrice;
        private List<CartResponse> carts;

        public CartUpdateResponse(List<Cart> cartList) {
            this.carts = cartList.stream().map(CartResponse::of).collect(Collectors.toList());
            this.totalPrice = cartList.stream().mapToInt(Cart::getPrice).sum();
        }
    }

}
