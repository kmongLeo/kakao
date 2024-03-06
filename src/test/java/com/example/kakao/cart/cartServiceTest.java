package com.example.kakao.cart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@SpringBootTest
public class cartServiceTest {

    @Autowired
    private CartJPARepository cartJPARepository;

    @Test
    @Transactional
    void findCart(){
        List<Cart> cartList = cartJPARepository.findByOptionIdAndUserId(1,1);

//        List<Map<String, Object> map = cartList;
    }

}
