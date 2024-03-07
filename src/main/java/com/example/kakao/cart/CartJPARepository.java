package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;



public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByUserId(int cartId);

    List<Cart> findByOptionIdAndUserId(int optionId, int userId);

    Optional<Cart> findCartByOptionIdAndUserId(int optionId, int userId);

    void deleteByUserId(int UserId);
}
