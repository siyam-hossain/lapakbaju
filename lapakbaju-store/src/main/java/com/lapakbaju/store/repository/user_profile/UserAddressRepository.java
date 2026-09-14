package com.lapakbaju.store.repository.user_profile;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.entity.user_profile.UserAddressEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAddressRepository extends CrudRepository<UserAddressEntity, Long> {

}
