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

    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional(readOnly = true)
    public List<ProductResponse.FindAllDTO> getAllProductsPaging(int page){
        PageRequest pageable = PageRequest.of(page, 10);
        Page<Product> products = productJPARepository.findAll(pageable);

        List<ProductResponse.FindAllDTO> productList = products.getContent().stream()
                .map(ProductResponse.FindAllDTO::new)
                .collect(Collectors.toList());
        return productList;
    }


    @Transactional(readOnly = true)
    public ProductResponse.ProductDetailDTO getProductDetail(int id) {
        Product product = productJPARepository.findById(id).orElseThrow(() -> new Exception404("no products: "+id));
        List<Option> option = optionJPARepository.findByProductId(id);
        return new ProductResponse.ProductDetailDTO(product, option);
    }
}
