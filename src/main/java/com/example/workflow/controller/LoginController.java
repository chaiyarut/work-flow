package com.example.workflow.controller;

import com.example.workflow.data.request.LoginRequest;
import com.example.workflow.service.LoginService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController extends MainController{

    @Autowired
    private LoginService loginService;

    @PostMapping("/auth")
    public ResponseEntity<?> loginWithLdap(@RequestBody LoginRequest loginRequest){

        var response = loginService.execute(loginRequest);

        return returnObject(response);
    }
}
