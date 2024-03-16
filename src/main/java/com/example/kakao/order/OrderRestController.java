package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.option.OptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final OrderService orderService;

    // (기능12) 결재
    @Operation(summary = "장바구니 목록 결제 API"
            , description = "해당의 유저의 장바구니에 담긴 목록을 결제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 성공"),
            @ApiResponse(responseCode = "400", description = "해당 유저의 장바구니 없음")})
    @PostMapping("/orders/save")
    public ResponseEntity<?> order(@AuthenticationPrincipal CustomUserDetails user){
        OrderResponse order = orderService.order(user.getUser());
        return ResponseEntity.ok(ApiUtils.success(order));
    }

    // (기능13) 주문 결과 확인
    @Operation(summary = "주문결과 확인 API"
            , description = "해당의 유저의 결제된 내역의 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "해당 ID에 대한 주문 없음")})
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrdersById(
            @Parameter(name= "id",description = "order id", example = "3")
            @PathVariable("id") int orderId, @AuthenticationPrincipal CustomUserDetails user) {
        OrderResponse order = orderService.getOrdersById(orderId, user.getUser());
        return ResponseEntity.ok(ApiUtils.success(order));
    }


    // 사용 안함
    //@PostMapping("/orders/clear")


//    @PostMapping("/orders/save")
//    public ResponseEntity<?> saveOrderV1(@RequestBody @Valid List<OrderRequest.SaveItemDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
//        OrderResponse.SaveDTO responseDTO = orderService.saveOrder(requestDTOs, userDetails.getUser());
//        return ResponseEntity.ok(ApiUtils.success(responseDTO));
//    }

}
