package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao.option.Option;
import com.example.kakao.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;

    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void addCardList(List<CartRequest.SaveDTO> requestDTOs, User user){
        // 동일한 옵션이 들어오면 예외처리
        Set<Integer> optionIds = new HashSet<>();
        for(CartRequest.SaveDTO cart : requestDTOs){
            if(!optionIds.add(cart.getOptionId())){ // * boolean add
                throw new Exception400("동일한 옵션이 중복되어 들어왔습니다 : " + cart.getOptionId());
            }
        }

        List<Cart> cartList = requestDTOs.stream().map(saveDTO -> {
            Option optionPS = optionJPARepository.findById(saveDTO.getOptionId()).orElseThrow(
                    ()-> new Exception404("해당 옵션을 찾을 수 없습니다. : " + saveDTO.getOptionId())
            );
            return saveDTO.toEntity(optionPS, user);

        }).collect(Collectors.toList());


        // option * getPrice() * quantity
        cartList.forEach(cart -> {
            try{
                Optional<Cart> cartOP = cartJPARepository.findByOptionIdAndUserId(cart.getOption().getId(), user.getId());

                if(cartOP.isPresent()){
                    Cart cartPS = cartOP.get();
                    int updateQuantity = cartPS.getQuantity()+cart.getQuantity();
                    cartPS.update(updateQuantity, cartPS.getOption().getPrice() * updateQuantity);
                }

            }catch(Exception e){
                throw new Exception500("장바구니 담기 중에 오류가 발생했습니다ㅣ : " + e.getMessage());
            }
        });
    }


}
