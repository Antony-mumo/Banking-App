package com.company.system.security;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import java.text.ParseException;
import java.util.Objects;
import java.util.Optional;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class SecurityContextHelper {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(SecurityContextHelper.class);

    private SecurityContextHelper() {
    }

    public static Optional<Jwt> getSessionJWT() {
        Authentication var1 = SecurityContextHolder.getContext().getAuthentication();
        if (var1 instanceof JwtAuthenticationToken authentication) {
            return !authentication.isAuthenticated() ? Optional.empty() : Optional.ofNullable((Jwt)authentication.getToken());
        } else {
            return Optional.empty();
        }
    }

    public static String getGlobalAuthUserIdentity() {
        return (String)getSessionJWT().map((jwt) -> {
            JWTClaimsSet claims;
            try {
                claims = JWTParser.parse(jwt.getTokenValue()).getJWTClaimsSet();
            } catch (ParseException var3) {
                log.error(var3.getLocalizedMessage());
                return null;
            }

            Object sub = claims.getClaim("sub");
            return Objects.isNull(sub) ? "ANONYMOUS" : String.valueOf(sub);
        }).orElse("ANONYMOUS");
    }
}