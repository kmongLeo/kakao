package com.example.kakao.user;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception401;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.JwtTokenProvider;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public User join(UserRequest request) {
        try{
            if(userJPARepository.existsByUsername(request.getUsername())){
                throw new Exception500("duplicated user");
            }

            String password = passwordEncoder.encode(request.getPassword());

            //TODO: 리팩토링 필요
            List<String> roles = new ArrayList<>();
            roles.add("ROLE_USER");
            User user = request.toUserEntity(password, roles);

            userJPARepository.save(user);
        } catch (Exception e){
            throw new Exception401("failed to join");
        }

        return userJPARepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new Exception500("cannot find user : "+ request.getUsername()));
    }

    @Transactional
    public String login(UserRequest request) {
        User user = userJPARepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new Exception500("cannot find email : "+request.getEmail()));

        if (passwordEncoder.matches(passwordEncoder.encode(request.getPassword()), user.getPassword())) {
            throw new Exception401("wrong email or password");
        }

        String token = jwtTokenProvider.create(user);

        return token;
    }
}
