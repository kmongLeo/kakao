package com.example.kakao.option;

import lombok.*;


public class OptionResponse {

    @Getter
    @Setter
    public static class FindAllDTO{
        private String optionName;
        private int price;
        private int productId;

        public FindAllDTO(Option option) {
            this.optionName = option.getOptionName();
            this.price = option.getPrice();
            this.productId = option.getProduct().getId();
        }
    }


    @Getter
    @Setter
    public static class ProductOptionDTO{
        private int id;
        private String optionName;
        private int price;
        private int productId;

        public ProductOptionDTO(Option option) {
            this.id = option.getId();
            this.optionName = option.getOptionName();
            this.price = option.getPrice();
            this.productId = option.getProduct().getId();
        }
    }
}
