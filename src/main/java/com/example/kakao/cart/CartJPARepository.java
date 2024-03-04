package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;



public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUserId(int cartId);

    @Query("select c from cart c where c.id in :cartIds")
    List<Cart> findByCartIds(@Param("cartIds") List<String> cartIds);
}
