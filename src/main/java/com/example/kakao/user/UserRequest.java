package com.example.kakao.user;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collections;

@Getter
@NoArgsConstructor
public class UserRequest {

    private String username;
    private String email;
    private String password;

    public User toUserEntity(String password, List<String> roles){
        return User.builder()
                .username(username)
                .email(email)
                .roles(roles)
                .password(password)
                .build();
    }

}
