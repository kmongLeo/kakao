package com.example.kakao.cart;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.CustomUserDetailsService;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.option.OptionResponse;
import com.example.kakao.user.User;
import com.example.kakao.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.header.Header;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.kakao._core.utils.ApiUtils.ApiResult;

import javax.validation.Valid;

import static com.example.kakao._core.utils.ApiUtils.success;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
public class CartRestController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetails;

    // (기능8) 장바구니 담기
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCart(@RequestBody List<CartRequest> request, @AuthenticationPrincipal CustomUserDetails user){
        cartService.addCart(request,user.getUser());
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // (기능11) 주문하기 - (장바구니 업데이트)
    @PostMapping("/carts/update")
    public ResponseEntity<?> updateCart(@RequestBody List<CartRequest> request, @AuthenticationPrincipal CustomUserDetails user){
        CartResponse.CartUpdateResponse response = cartService.updateCart(request, user.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
        return ResponseEntity.ok(apiResult);
    }

    // (기능9) 장바구니 보기 - (주문화면, 결재화면)
    @GetMapping("/carts")
    public List<CartResponse> getCart(@AuthenticationPrincipal CustomUserDetails user){
        return cartService.getCartList(user.getUser());
    }

    @PostMapping("/carts/clear")
    public void deleteCart(@AuthenticationPrincipal CustomUserDetails user){
        cartService.deleteCart(user.getUser());
    }
}
