package com.lapakbaju.store.repository.cart;

import com.lapakbaju.store.entity.cart.CartItemEntity;
import com.lapakbaju.store.entity.authentication.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItemEntity, Long> {

    List<CartItemEntity> findByUser(UserEntity user);

    Optional<CartItemEntity> findByUserAndProductId(UserEntity user, Long productId);

    void deleteByUser(UserEntity user);
}