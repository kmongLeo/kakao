package com.example.kakao.order;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.option.OptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final OrderService orderService;

    // (기능12) 결재
    @PostMapping("/orders/save")
    public ResponseEntity<?> order(@AuthenticationPrincipal CustomUserDetails user){
        OrderResponse order = orderService.order(user.getUser());
        return ResponseEntity.ok(ApiUtils.success(order));
    }

    // (기능13) 주문 결과 확인
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrdersById(@PathVariable("id") int orderId, @AuthenticationPrincipal CustomUserDetails user) {
        OrderResponse order = orderService.getOrdersById(orderId, user.getUser());
        return ResponseEntity.ok(ApiUtils.success(order));
    }


    // 사용 안함
    //@PostMapping("/orders/clear")


//    @PostMapping("/orders/save")
//    public ResponseEntity<?> saveOrderV1(@RequestBody @Valid List<OrderRequest.SaveItemDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
//        OrderResponse.SaveDTO responseDTO = orderService.saveOrder(requestDTOs, userDetails.getUser());
//        return ResponseEntity.ok(ApiUtils.success(responseDTO));
//    }

}
