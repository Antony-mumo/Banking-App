package com.company.system.services;

import com.company.system.controller.ResponseWrapper;
import com.company.system.controller.user.body.AccessToken;
import com.company.system.controller.user.body.LoginUserReq;
import com.company.system.controller.user.body.RegisterUserReq;
import com.company.system.models.User;
import com.company.system.repository.UserRepository;
import com.company.system.security.SecurityContextHelper;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.jwk.*;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Value("${data.security.secretkey}")
    private String secretKey;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public ResponseWrapper<AccessToken> loginUser(LoginUserReq loginUserRequest) {
        return userRepository.findByEmail(loginUserRequest.getEmail())
                .filter(user -> passwordMatches(user, loginUserRequest))
                .map(theUser -> {
                    var accessToken = new AccessToken();
                    String jwt = createJwt(theUser);
                    accessToken.setToken(jwt);
                    accessToken.setExpiresIn(60L * 60L * 1000L);
                    return accessToken;
                })
                .map(token -> {
                    var res = new ResponseWrapper<AccessToken>();
                    res.setSuccess(token);
                    return res;
                })
                .orElseGet(() -> {
                    var res = new ResponseWrapper<AccessToken>();
                    res.failed(HttpStatus.NOT_FOUND, "Wrong password or invalid user name");
                    return res;
                });
    }

    private String createJwt(User theUser) {

        // RSA signatures require a public and private RSA key pair, the public key
        // must be made known to the JWS recipient in order to verify the signatures
        RSAKey rsaJWK = null;
        try {
            rsaJWK = new RSAKeyGenerator(2048)
                    .keyID(secretKey)
                    .generate();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        // Create RSA-signer with the private key
        JWSSigner signer = null;
        try {
            signer = new RSASSASigner(rsaJWK);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        // Prepare JWT with claims set
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(theUser.getRefId())
                .claim("email", theUser.getEmail())
                .claim("firstname", theUser.getFirstname())
                .claim("lastname", theUser.getLastname())
                .issuer("https://iam.com")
                .expirationTime(new Date(new Date().getTime() + (60 * 60 * 1000)))
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaJWK.getKeyID()).build(),
                claimsSet);

        // Compute the RSA signature
        try {
            signedJWT.sign(signer);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        // To serialize to compact form, produces something like
        // eyJhbGciOiJSUzI1NiJ9.SW4gUlNBIHdlIHRydXN0IQ.IRMQENi4nJyp4er2L
        // mZq3ivwoAjqa1uUkSBKFIX7ATndFF5ivnt-m8uApHO4kfIFOrW7w2Ezmlg3Qd
        // maXlS9DhN0nUk_hGI3amEjkKd0BWYCB8vfUbUv0XGjQip78AI4z1PrFRNidm7
        // -jPDm5Iq0SZnjKjCNS5Q15fokXZc8u0A
        return signedJWT.serialize();
    }

    public ResponseWrapper<String> signUp(RegisterUserReq req) {
        var user = new User();
        user.setEmail(req.getEmail());
        user.setUserName(req.getUserName());
        user.setFirstname(req.getFirstname());
        user.setLastname(req.getLastname());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepository.save(user);
        var res = new ResponseWrapper<String>();
        res.setSuccess("User registered!");
        return res;
    }

    private boolean passwordMatches(User user, LoginUserReq loginUserRequest) {
        var password = loginUserRequest.getPassword();
        var encryptedPassword = user.getPassword();
        return passwordEncoder.matches(password, encryptedPassword);
    }

    public ResponseWrapper<User> currentUser() {
        var currentUser = SecurityContextHelper.getGlobalAuthUserIdentity();
        return userRepository.findByRefId(currentUser)
                .map(theUser -> {
                    var res = new ResponseWrapper<User>();
                    res.setSuccess(theUser);
                    return res;
                })
                .orElseGet(()-> {
                    var res = new ResponseWrapper<User>();
                    res.failed(HttpStatus.NOT_FOUND, "current user details not found");
                    return res;
                });
    }
}
