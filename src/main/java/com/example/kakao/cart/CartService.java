package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao.option.Option;
import com.example.kakao.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Transactional
    public void addCart(int userId, CartRequest request){
        Option option = optionJPARepository.findById(request.getOptionId())
                .orElseThrow(()-> new Exception404("no option : "+ request.getOptionId()));

        User user = userJPARepository.findById(userId)
                .orElseThrow(()-> new Exception404("no user : "+ request.getUserId()));

        Cart cart = request.toCartEntity(option,user);

        cartJPARepository.save(cart);
    }

    @Transactional
    public void updateCart(List<CartRequest> request) {
        //TODO: 미완성
        for(CartRequest c : request){
            Cart cart = cartJPARepository.findById(c.getCartId())
                    .orElseThrow(()-> new Exception404("no cart : "+ c.getCartId()));

            cart.update(cart.getQuantity(), cart.getPrice());

            cartJPARepository.save(cart);
        }
    }

    //TODO: 리턴값 형태
    public CartResponse getCartList(int userId) {
        Cart cart = cartJPARepository.findByUserId(userId)
                .orElseThrow(()-> new Exception404("no cart for user : "+ userId));

        Option option = optionJPARepository.findById(cart.getOption().getId())
                .orElseThrow(()-> new Exception404("no option : "+ cart.getOption().getId()));

        return CartResponse.of(cart, option);
    }

    @Transactional
    public void deleteCart(int cartId) {
        cartJPARepository.deleteById(cartId);
    }
}
