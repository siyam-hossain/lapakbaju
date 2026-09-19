package com.lapakbaju.store.repository.user_profile;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.entity.user_profile.UserOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Long> {
    List<UserOrderEntity> findByUserId(Long id);
    List<UserOrderEntity> findByUser(UserEntity user);
}
