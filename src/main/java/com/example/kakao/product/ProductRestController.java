package com.example.kakao.product;

import com.example.kakao._core.utils.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "상품 조회 API", description = "상품을 조회하는 API 이며, 페이징을 포함합니다.")
    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponse>> getProducts(
            @Parameter(description = "page number", example = "3", required = true)
            @RequestParam(value = "page", defaultValue="0") int page){
        Page<ProductResponse> productResponse = productService.getAllProductsPaging(page);
        return ResponseEntity.ok(productResponse);
    }

    @Operation(summary = "상품 조회 API", description = "상품을 조회하는 API 이며, 페이징을 포함하지 않습니다.")
    @GetMapping("/productList")
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    @Operation(summary = "상품 조회 API", description = "해당 id의 상품을 조회합니다.")
    @GetMapping("/products/{id}")
    public ProductResponse getProduct(
            @Parameter(name = "id", description = "product id", example = "1")
            @PathVariable("id") int id){
        return productService.getProduct(id);
    }

}