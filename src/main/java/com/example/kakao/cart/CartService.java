package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
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
            HashSet<Integer> set = new HashSet<>();
            if (set.contains(req.getOptionId())) { //optionid 중복 확인
                throw new Exception400("duplicate option");
            }
            else set.add(req.getOptionId());

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
                        .price(option.getPrice() * req.getQuantity())
                        .build();

                cartJPARepository.save(newCart);
            }
            else { // 있음
                //cart 해당 옵션의 quantity update
                Cart oldCart = cartJPARepository.findCartByOptionIdAndUserId(req.getOptionId(), user.getId())
                        .orElseThrow(() -> new Exception400("there is no cart"));

                oldCart.update(req.getQuantity(), oldCart.getOption().getPrice() * req.getQuantity());
            }

        }
    }

    @Transactional
    public CartResponse.CartUpdateResponse updateCart(List<CartRequest> request, User user) {  //cartId 같이 들어옴
        // 1. cartId 중복
        HashSet<Integer> set = new HashSet<>();
        for(CartRequest req : request) {
            if (set.contains(req.getCartId())) { //cartId 중복 확인
                throw new Exception400("duplicate cart : "+ req.getCartId());
            } else set.add(req.getCartId());
        }

        // 2. 유저의 carts 확인 <-> 들어온 요청 carts 비교
        List<Cart> userCartList = cartJPARepository.findByUserId(user.getId());

        //암것도 없으면
        if (userCartList.isEmpty()) {
            throw new Exception404("there is no cart with user : " + user.getId());
        }

        //있음
        for(CartRequest req : request){
            for(Cart cart : userCartList){
                if(cart.getId() == req.getCartId()){
                    cart.update(req.getQuantity(), cart.getOption().getPrice() * req.getQuantity());
                }
            }
        }

        return new CartResponse.CartUpdateResponse(userCartList);

    }

    public List<CartResponse> getCartList(User user) {
        List<Cart> cart = cartJPARepository.findByUserId(user.getId());
        return cart.stream().map(CartResponse::of).collect(Collectors.toList());
    }

    @Transactional
    public void deleteCart(User user) {
        cartJPARepository.deleteByUserId(user.getId());
    }
}
