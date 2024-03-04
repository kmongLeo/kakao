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
    public ResponseEntity addCart(@AuthenticationPrincipal CustomUserDetails user, @RequestBody CartRequest request){
        cartService.addCart(user.getUser().getId(), request);
        return ResponseEntity.ok().build();
    }

    // (기능11) 주문하기 - (장바구니 업데이트)
    @PostMapping("/carts/update")
    public void updateCart(@RequestBody List<CartRequest> request){
        cartService.updateCart(request);
    }

    // (기능9) 장바구니 보기 - (주문화면, 결재화면)
    @GetMapping("/carts")
    public CartResponse getCart(@AuthenticationPrincipal CustomUserDetails user){
        return cartService.getCartList(user.getUser().getId());
    }

    @PostMapping("/carts/clear")
    public void deleteCart(@RequestParam int cartId){
        cartService.deleteCart(cartId);
    }
}
