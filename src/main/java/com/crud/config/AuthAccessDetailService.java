package com.crud.config;

import com.crud.entity.AuthRequest;
import com.crud.repo.AuthRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthAccessDetailService implements UserDetailsService {

    private final AuthRequestRepository employeeRepository;

    public AuthAccessDetailService(AuthRequestRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("START :: AuthAccessDetailService --> loadUserByUsername");
        Optional<AuthRequest> employeeInfo = employeeRepository.findByName(username);
        return employeeInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }
}
