package com.andresalarcon.tokengenerator.application.service;

import com.andresalarcon.tokengenerator.application.dto.LoginRequest;

public interface IAuthService {
    Object authenticateByJWT(LoginRequest request);
}
