package com.andresalarcon.tokengenerator.infrastructure.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secret;
    private Integer expiration;
    private String type;

    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }

    public Integer getExpiration() { return expiration; }
    public void setExpiration(Integer expiration) { this.expiration = expiration; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
