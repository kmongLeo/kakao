package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.option.Option;
import com.example.kakao.option.OptionJPARepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ProductService {

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProductsPaging(int page){
        PageRequest pageable = PageRequest.of(page, 10);
        Page<Product> products = productJPARepository.findAll(pageable);
        Page<ProductResponse> productResponses = products.map(p -> ProductResponse.builder()
                .id(p.getId())
                .productName(p.getProductName())
                .description(p.getDescription())
                .price(p.getPrice())
                .image(p.getImage())
                .build());

        return productResponses;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts(){
        List<Product> productList = productJPARepository.findAll();
        return productList.stream().map(ProductResponse::of).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public ProductResponse getProduct(int id) {
        Product product = productJPARepository.findById(id).orElseThrow(() -> new Exception404("no products: "+id));
        List<Option> option = optionJPARepository.findByProductId(id);

        ProductResponse response = ProductResponse.of(product, option);
        return response;
    }
}
