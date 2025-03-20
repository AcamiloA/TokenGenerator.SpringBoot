package com.andresalarcon.tokengenerator.infrastructure.security;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class BearerUtil {

    private final BearerProperties bearerProperties;

    public BearerUtil(BearerProperties bearerProperties) {
        this.bearerProperties = bearerProperties;
    }

    public String generateBearer() {
        String secretKey = bearerProperties.getSecretKey();
        return "Bearer " + hashWithHmac(UUID.randomUUID().toString(), secretKey);
    }

    private String hashWithHmac(String data, String secretKey) {
        try {
            Mac hmac = Mac.getInstance(bearerProperties.getInstance());
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), bearerProperties.getInstance());
            hmac.init(secretKeySpec);
            byte[] hash = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (IllegalStateException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generando token seguro", e);
        }
    }
}
