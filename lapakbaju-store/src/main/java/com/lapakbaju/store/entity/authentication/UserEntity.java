package com.lapakbaju.store.entity.authentication;

import com.lapakbaju.store.entity.user_profile.UserAddressEntity;
import com.lapakbaju.store.entity.user_profile.UserOrderEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String role="USER";

    private String profileImage;

    @OneToOne
    private UserAddressEntity address;

    @OneToMany
    private List<UserOrderEntity> orders;

}
