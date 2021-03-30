package com.rafhael.barabas.apiauth.controllers;

import com.rafhael.barabas.apiauth.data.vo.UserVO;
import com.rafhael.barabas.apiauth.entities.User;
import com.rafhael.barabas.apiauth.jwt.JwtTokenProvider;
import com.rafhael.barabas.apiauth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthenticationManager am;
    private final JwtTokenProvider provider;
    private final UserRepository repository;

    @Autowired
    public AuthController(AuthenticationManager am, JwtTokenProvider provider, UserRepository repository) {
        this.am = am;
        this.provider = provider;
        this.repository = repository;
    }

    @PostMapping(consumes = {"application/json", "application/xml", "application/x-yaml"},
            produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> create(@RequestBody UserVO userVO) {
        try {
            var username = userVO.getUsername();
            var password = userVO.getPassword();

            am.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            var user = repository.findByUsername(username);
            var token = "";

            if(nonNull(user)){
                token = provider.createToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username not found");
            }

            Map<Object, Object> map = new HashMap<>();
            map.put("username", username);
            map.put("token", token);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(map);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid credentials (username/password)");
        }
    }

}
