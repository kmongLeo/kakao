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

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        List<Integer> cartIds = cartList.stream().map(cart -> cart.getId()).collect(Collectors.toList());
        List<Integer> requestIds =requestDTOs.stream().map(dto -> dto.getCartId()).collect(Collectors.toList());

        if(cartIds.size() == 0){
            throw new Exception404("주문할 장바구니 상품이 없습니다");
        }

        if(requestIds.stream().distinct().count() != requestIds.size()){
            throw new Exception400("동일한 장바구니 아이디를 주문할 수 없습니다");
        }

        for (Integer requestId : requestIds) {
            if(!cartIds.contains(requestId)){
                throw new Exception400("장바구니에 없는 상품은 주문할 수 없습니다 : "+requestId);
            }
        }

        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            for (Cart cart : cartList) {
                if(cart.getId() == updateDTO.getCartId()){
                    cart.update(updateDTO.getQuantity(), cart.getPrice() * updateDTO.getQuantity());
                }
            }
        }
        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹


    // this.totalPrice = cartList.stream().mapToInt(cart -> cart.getOption().getPrice() * cart.getQuantity()).sum();
    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartLists = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        return new CartResponse.FindAllDTO(cartLists);
    }

    @Transactional
    public void clear(User user) {
        try {
            cartJPARepository.deleteByUserId(user.getId());
        }catch (Exception e){
            throw new Exception500("장바구니 비우기 실패 : "+e.getMessage());
        }

    }
}
