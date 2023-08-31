package com.company.system.controller.user;

import com.company.system.controller.ResponseWrapper;
import com.company.system.controller.user.body.AccessToken;
import com.company.system.controller.user.body.LoginUserReq;
import com.company.system.controller.user.body.RegisterUserReq;
import com.company.system.models.User;
import com.company.system.services.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class UserController {
    private final UserService userService;
    @PostMapping("login")
    public ResponseWrapper<AccessToken> login(@RequestBody @Valid LoginUserReq loginUserRequest)  {
        return userService.loginUser(loginUserRequest);
    }

    @PostMapping("signup")
    public ResponseWrapper<String> signUp(@RequestBody @Valid RegisterUserReq req)  {
        return userService.signUp(req);
    }

    @GetMapping("current-user")
    public ResponseWrapper<User> currentUser()  {
        return userService.currentUser();
    }

}
