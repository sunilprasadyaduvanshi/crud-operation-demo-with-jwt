package com.crud.repo;

import com.crud.entity.AuthRequest;
import com.crud.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRequestRepository extends JpaRepository<AuthRequest, Integer> {
    Optional<AuthRequest> findByName(String username);
}
