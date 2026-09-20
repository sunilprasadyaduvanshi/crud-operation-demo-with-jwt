package com.crud.config;

import com.crud.entity.Employee;
import com.crud.repo.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class EmployeeInfoDetailService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public EmployeeInfoDetailService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("START :: EmployeeInfoDetailService --> loadUserByUsername");
        Optional<Employee> employeeInfo = employeeRepository.findByName(username);
        return employeeInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }
}
