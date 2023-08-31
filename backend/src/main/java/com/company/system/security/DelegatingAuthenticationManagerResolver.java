package com.company.system.security;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
public class DelegatingAuthenticationManagerResolver implements AuthenticationManagerResolver<HttpServletRequest> {

    @Value("${data.security.secretkey}")
    private String secretKey;
    private final JWTHelper jwtHelper = new JWTHelper();


    public AuthenticationManager resolve(HttpServletRequest httpServletRequest) {
        return this.resolveFromSHA256();
    }

    public AuthenticationManager resolveFromSHA256() {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(this.jwtHelper.jwtDecoder(this.secretKey));
        JWTHelper var10001 = this.jwtHelper;
        Objects.requireNonNull(var10001);
        provider.setJwtAuthenticationConverter(var10001::convert);
        Objects.requireNonNull(provider);
        return provider::authenticate;
    }
}
