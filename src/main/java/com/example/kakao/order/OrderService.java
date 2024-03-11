package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.option.Option;
import com.example.kakao.option.OptionJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse order(User user) {
        // 일단 장바구니 가져와
        List<Cart> carts = cartJPARepository.findByUserId(user.getId());
        if(carts.isEmpty()){ //없으면 던져
            throw new Exception400("there is no carts for user : "+ user.getId());
        }

        //주문
        Order order = Order.builder()
                .user(user)
                .build();

        orderJPARepository.save(order);

        //주문내역 저장 - carts = item
        List<Item> items = new ArrayList<>();
        int totalPrice = 0;
        for(Cart cart : carts){
            Item item = Item.builder()
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getQuantity())
                    .price(cart.getPrice())
                    .build();

            totalPrice += cart.getPrice();

            items.add(item);
        }
        itemJPARepository.saveAll(items);

        //장바구니 지워
        cartJPARepository.deleteByUserId(user.getId());

        return new OrderResponse(order, items, totalPrice);

    }

    public OrderResponse getOrdersById(int orderId, User user) {
        Order order = orderJPARepository.findById(orderId)
                .orElseThrow(() -> new Exception400("no orders : "+ orderId));

        List<Item> itemList = itemJPARepository.findAllByOrderId(orderId);

        //TODO:코드 반복됨 - 맘에 안듦
        int totalPrice = 0;
        for(Item item : itemList){
            totalPrice += item.getPrice();
        }

        return new OrderResponse(order, itemList, totalPrice);
    }
}
