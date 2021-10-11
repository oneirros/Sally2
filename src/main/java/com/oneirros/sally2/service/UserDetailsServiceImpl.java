package com.oneirros.sally2.service;

import com.oneirros.sally2.entity.UserEntity;
import com.oneirros.sally2.pojo.SecurityUserDetailsImpl;
import com.oneirros.sally2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User email: %s not found", email))
        );

        return new SecurityUserDetailsImpl(user.getRole(), user.getPassword(), user.getEmail());

    }
}
