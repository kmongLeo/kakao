package com.example.kakao.cart;

import com.example.kakao.option.Option;
import com.example.kakao.option.OptionResponse;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.stream.Collectors;





public class CartResponse {

    /*
     carts :[]
     totalprice
     */
    @Getter
    @Setter
    public static class CartUpdateDTO{
        private int totalPrice;
        private List<CartDTO> carts;

        public CartUpdateDTO(List<Cart> cartList) {
            this.carts = cartList.stream().map(CartDTO::new).collect(Collectors.toList());
            this.totalPrice = cartList.stream().mapToInt(Cart::getPrice).sum();
        }

        @Getter
        @Setter
        public class CartDTO{
            private int cartId;
            private int optionId;
            private String optionName;
            private int quantity;
            private int price;

            public CartDTO(Cart cart) {
                this.cartId = cart.getId();
                this.optionId = cart.getOption().getId();
                this.optionName = cart.getOption().getOptionName();
                this.quantity = cart.getQuantity();
                this.price = cart.getPrice();
            }
        }
    }


    /*
        * "products" :[
            "id"
            "productname"
            "carts" :[
                 "option": {}
            ]
            "total price" :
        ]*/
    @Getter
    @Setter
    public static class FindCartDTO {
        private List<ProductDTO> products;
        private int totalPrice;

        public FindCartDTO(List<Cart> cartList) {
            this.products = cartList.stream().map(cart -> cart.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, cartList)).collect(Collectors.toList());
            this.totalPrice = cartList.stream().mapToInt(Cart::getPrice).sum();
        }

        @Getter
        @Setter
        public class ProductDTO{
            private int id;
            private String productName;

            public ProductDTO(Product product, List<Cart> carts) {
                this.id = product.getId();
                this.productName = product.getProductName();
            }

            @Getter
            @Setter
            public class CartDTO{
                private int id;
                private OptionDTO option;
                private int quantity;
                private int price;

                public CartDTO(Cart cart) {
                    this.id = cart.getId();
                    this.option = new OptionDTO(cart.getOption());
                    this.quantity = cart.getQuantity();
                    this.price = cart.getOption().getPrice() * cart.getQuantity();
                }

                @Getter
                @Setter
                public class OptionDTO{
                    private int id;
                    private String optionName;
                    private int price;

                    public OptionDTO(Option option) {
                        this.id = option.getId();
                        this.optionName = option.getOptionName();
                        this.price = option.getPrice();
                    }
                }
            }

        }

    }

}
