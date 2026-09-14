package com.lapakbaju.store.service.authentication;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.repository.authentication.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<UserEntity> user = repository.findByEmail(email);
        if (user.isPresent()) {
            var userObj = user.get();

            return User.builder()
                    .username(userObj.getEmail())
                    .password(userObj.getPassword())
                    .roles(userObj.getRole())
                    .build();
        }else  {
            throw new UsernameNotFoundException("User not found" + email);
        }
    }
}
