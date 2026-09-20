package com.crud.service;

import com.crud.entity.AuthRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthRequestService {
    AuthRequest saveAuthRequest(AuthRequest authRequest);
}
