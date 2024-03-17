package com.example.kakao.order;

import com.example.kakao.option.Option;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/*
* <결제내역>
* 주문번호
* 결제된 product
* 그 product의 option -> item에 있음
* 총 금액
* */
@Getter
@Setter
public class OrderResponse {

    private int orederId;
    private List<OrderResponse.ProductDTO> productList;
    private int totalPrice;

    public OrderResponse(Order order, List<Item> itemList) {
        this.orederId = order.getId();
        this.productList = itemList.stream()
                .map(item -> item.getOption().getProduct()).distinct()
                .map(product -> new ProductDTO(product, itemList))
                .collect(Collectors.toList());
        this.totalPrice = itemList.stream()
                .mapToInt(item -> item.getOption().getPrice()*item.getQuantity())
                .sum();
    }

    @Getter
    public static class ProductDTO{
        private String productName;
        private List<OrderResponse.ProductDTO.ItemDTO> itemList;

        public ProductDTO(Product product, List<Item> items) {
            this.productName = product.getProductName();
            this.itemList = items.stream()
                    .map(item -> new ItemDTO(item, item.getOption()))
                    .collect(Collectors.toList());

        }

        @Getter
        public class ItemDTO{
            private int id;
            private String optionName;
            private int quantity;
            private int price;

            public ItemDTO(Item item, Option option) {
                this.id = item.getId();
                this.optionName = option.getOptionName();
                this.quantity = item.getQuantity();
                this.price = option.getPrice() * item.getQuantity();
            }
        }

    }

}
