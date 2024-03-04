package com.example.kakao.product;

import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductRestController {

    @Autowired
    private ProductService productService;

    // TODO : product 전체 조회
    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponse>> getProducts(@RequestParam(value = "page", defaultValue="0") int page){
        Page<ProductResponse> productResponse = productService.getAllProductsPaging(page);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/productList")
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }


    // TODO : (기능5) 개별 상품 상세 조회
    @GetMapping("/products/{id}")
    public ProductResponse getProduct(@PathVariable("id") int id){
        return productService.getProduct(id);
    }

}