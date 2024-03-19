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
    public ResponseEntity<?> getProducts(
            @Parameter(description = "page number", example = "3", required = true)
            @RequestParam(value = "page", defaultValue="0") int page){
        List<ProductResponse.FindAllDTO> response  = productService.getAllProductsPaging(page);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
        return ResponseEntity.ok(apiResult);
    }


    @Operation(summary = "상품 조회 detail API", description = "해당 id의 상품을 조회합니다.")
    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductDetail(
            @Parameter(name = "id", description = "product id", example = "1")
            @PathVariable("id") int id){
        ProductResponse.ProductDetailDTO response =productService.getProductDetail(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
        return ResponseEntity.ok(apiResult);
    }

}