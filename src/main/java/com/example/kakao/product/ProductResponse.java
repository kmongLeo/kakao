package com.example.kakao.product;

import com.example.kakao.option.Option;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ProductResponse {

    @Getter
    @Setter
    public static class FindAllDTO{
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;

        public FindAllDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
        }
    }

    /*
    * product detail{
    *   options[]
    * }
    *
    * */
    @Getter
    @Setter
    public static class ProductDetailDTO{
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;

        private List<OptionDTO> option;

        public ProductDetailDTO(Product product, List<Option> option) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
            this.option = option.stream().map(option1 -> new OptionDTO(option1))
                    .collect(Collectors.toList());
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
