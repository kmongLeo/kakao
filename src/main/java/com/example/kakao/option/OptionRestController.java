package com.example.kakao.option;

import com.example.kakao._core.utils.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OptionRestController {

    @Autowired
    private OptionService optionService;

    /**
     * @param id
     * ProductId에 해당하는 Option 조회
     * @return
     * 성공 시 Option 리스트 반환
     */
    @Operation(summary = "상품 옵션 조회 API", description = "해당 product의 option을 조회합니다.")
    @GetMapping("/products/{id}/options")
    public ResponseEntity<?> getProductOptions(
            @Parameter(name= "id",description = "product id", example = "1")
            @PathVariable("id") int id){
         List<OptionResponse.ProductOptionDTO> optionResponses = optionService.getProductOptions(id);
         return ResponseEntity.ok().body(optionResponses);
     }


    /**
     * @return
     * Option 전체 반환
     */
    @Operation(summary = "전체 옵션 조회 API", description = "전체 option을 조회합니다.")
    @GetMapping("/options")
    public ResponseEntity<?> getAllOptions(){
        List<OptionResponse.FindAllDTO> response = optionService.findAll();
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
        return ResponseEntity.ok(apiResult);
     }

}
