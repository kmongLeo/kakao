package com.example.kakao.product;

import com.example.kakao.option.Option;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponse {

    private int id;
    private String productName;
    private String description;
    private String image;
    private int price;

    private List<Option> options;

    @Builder
    public ProductResponse(int id, String productName, String description, String image, int price,
            List<Option> options) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
        this.options = options;
    }

    public static ProductResponse of(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .build();
    }

    public static ProductResponse of(Product product, List<Option> options){
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .options(options)
                .build();
    }

}
