package com.example.kakao.cart;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.option.OptionResponse;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    // TODO : (기능1) 장바구니 담기
    //@PostMapping("/carts/add")

    // TODO : (기능2) 주문하기 - (장바구니 업데이트)
    //@PostMapping("/carts/update")

    // TODO : (기능3) 장바구니 보기 - (주문화면, 결재화면)
    //@GetMapping("/carts")

    // TODO : (기능4) 모두 삭제
    //@PostMapping("/carts/clear")

}
