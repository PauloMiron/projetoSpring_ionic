package com.PauloChaves.ProjetoCursoUdemy.resources.utils;

import com.PauloChaves.ProjetoCursoUdemy.dto.EmailDTO;
import com.PauloChaves.ProjetoCursoUdemy.security.JWTUtil;
import com.PauloChaves.ProjetoCursoUdemy.security.UserSS;
import com.PauloChaves.ProjetoCursoUdemy.services.AuthService;
import com.PauloChaves.ProjetoCursoUdemy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @PostMapping
    @RequestMapping(value="/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @RequestMapping(value="/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
        service.sendNewPassword(objDto.getEmail());
        return ResponseEntity.noContent().build();

    }
}
