package com.example.kakao.cart;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartRestController {

    private final CartService cartService;

    // (기능8) 장바구니 담기
    @Operation(summary = "장바구니 추가 API", description = "해당 유저의 장바구니에 상품을 추가합니다. 동일한 상품과 옵션은 추가할 수 없습니다.")
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCart(
            @RequestBody List<CartRequest> request, @AuthenticationPrincipal CustomUserDetails user){
        cartService.addCart(request,user.getUser());
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // (기능11) 주문하기 - (장바구니 업데이트)
    @Operation(summary = "장바구니 상품 update API", description = "해당 유저의 장바구니 상품의 option 을 수정합니다.")
    @PostMapping("/carts/update")
    public ResponseEntity<?> updateCart(
            @RequestBody List<CartRequest> request, @AuthenticationPrincipal CustomUserDetails user){
        CartResponse.CartUpdateDTO response = cartService.updateCart(request, user.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
        return ResponseEntity.ok(apiResult);
    }

    // (기능9) 장바구니 보기 - (주문화면, 결재화면)
    @Operation(summary = "장바구니 조회 API", description = "해당 유저의 장바구니 상품을 조회합니다.")
    @GetMapping("/carts")
    public ResponseEntity<?> getCart(@AuthenticationPrincipal CustomUserDetails user){
        CartResponse.FindCartDTO response = cartService.getCartList(user.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
        return ResponseEntity.ok(apiResult);
    }

    @Operation(summary = "장바구니 삭제 API", description = "해당 유저의 장바구니를 삭제합니다.")
    @PostMapping("/carts/clear")
    public ResponseEntity<?> deleteCart(@AuthenticationPrincipal CustomUserDetails user){
        cartService.deleteCart(user.getUser());
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
