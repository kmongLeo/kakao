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
    public void addCart(List<CartRequest> request, User user){
        // [ { optionId:1, quantity:5 }, { optionId:2, quantity:5 } ]
        for(CartRequest req : request) {
            // 중복처리??

            // 1. 유저&option의 cart 가 있는지 확인
            List<Cart> cart = cartJPARepository.findByOptionIdAndUserId(req.getOptionId(), user.getId());

            if (cart.isEmpty()) {
                Option option = optionJPARepository.findById(req.getOptionId())
                        .orElseThrow(() -> new Exception404("no option : " + req.getOptionId()));

                //cart 없으면 new cart save
                Cart newCart = Cart.builder()
                        .user(user)
                        .quantity(req.getQuantity())
                        .option(option)
                        .price(req.getOptionId() * req.getQuantity())
                        .build();

                cartJPARepository.save(newCart);
            }
            else { // 이미 있음
                //cart 해당 옵션의 quantity update
            }

        }
    }

    @Transactional
    public void updateCart(List<CartRequest> request, User user) {  //cartId 같이 들어옴
        // 1. 유저의 carts 확인 <-> 들어온 요청 carts 비교
        List<Cart> userCartList = cartJPARepository.findByUserId(user.getId());

        for(CartRequest req : request){
            Cart cart = cartJPARepository.findById(req.getCartId())
                    .orElseThrow(()-> new Exception404("no cart : "+ req.getCartId()));

            cart.update(cart.getQuantity(), cart.getPrice());

            cartJPARepository.save(cart);
        }
    }

    //TODO: 리턴값 형태
    public CartResponse getCartList(int userId) {
//        Cart cart = cartJPARepository.findByUserId(userId)
//                .orElseThrow(()-> new Exception404("no cart for user : "+ userId));
//
//        Option option = optionJPARepository.findById(cart.getOption().getId())
//                .orElseThrow(()-> new Exception404("no option : "+ cart.getOption().getId()));
//
//        return CartResponse.of(cart, option);
        return null;
    }

    @Transactional
    public void deleteCart(int cartId) {
        cartJPARepository.deleteById(cartId);
    }
}
