package com.yrkim.yrkimapi.security;

import com.yrkim.yrkimapi.exception.UserNotFoundException;
import com.yrkim.yrkimapi.model.entity.User;
import com.yrkim.yrkimapi.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User not found with this username : %s", username)));
        return getUserDetails(user);
    }

    private CustomUserDetails getUserDetails(User user) {
        CustomUserDetails userDetail = new CustomUserDetails();
        userDetail.setId(user.getId());
        userDetail.setEmail(user.getEmail());
        userDetail.setRoles(user.getRoles());
        userDetail.setUser(user.toDto());
        return userDetail;
    }
}
