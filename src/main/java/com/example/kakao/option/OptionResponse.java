package com.example.kakao.option;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionResponse {

    private String optionName;
    private int price;
    private int productId;

    @Builder
    public OptionResponse(String optionName, int price, int productId) {
        this.optionName = optionName;
        this.price = price;
        this.productId = productId;
    }

    public static OptionResponse of(Option option) {
        return OptionResponse.builder()
                .optionName(option.getOptionName())
                .price(option.getPrice())
                .productId(option.getProduct().getId())
                .build();
    }
}
