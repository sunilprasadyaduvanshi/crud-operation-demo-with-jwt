package com.crud.service;

import com.crud.entity.AuthRequest;
import com.crud.repo.AuthRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthRequestServiceImpl implements AuthRequestService {

    private final AuthRequestRepository authRequestRepository;
    public AuthRequestServiceImpl(AuthRequestRepository authRequestRepository) {
        this.authRequestRepository = authRequestRepository;
    }

    @Override
    public AuthRequest saveAuthRequest(AuthRequest authRequest) {
        authRequestRepository.save(authRequest);
        return authRequest;
    }
}
