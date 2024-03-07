package com.example.kakao.product;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJPARepository extends JpaRepository<Product, Integer> {
    Optional<Product> findById(int id);
}
