package com.example.kakao.user;

import com.example.kakao._core.security.JwtTokenProvider;
import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserRestController {


    // (기능1) 회원가입
    // @PostMapping("/join")


    // (기능2) 로그인
    // @PostMapping("/login")


    // 사용 안함 - 프론트 요구사항에 이메일 중복 검사 로직 없음.
    // @PostMapping("/check")

    // (기능3) - 로그아웃
    // 사용 안함 - 프론트에서 localStorage JWT 토큰을 삭제하면 됨.
    // @GetMapping("/logout")

}