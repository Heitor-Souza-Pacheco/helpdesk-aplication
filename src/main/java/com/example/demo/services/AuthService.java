package com.example.demo.services;

import com.example.demo.DTO.AcessDTO;
import com.example.demo.DTO.AuthenticationDTO;
import com.example.demo.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AcessDTO login(AuthenticationDTO authDTO) {

        try {


            UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(userAuth);

            UserDetailImpl userAuthenticate = (UserDetailImpl) authentication.getPrincipal();

            String token = jwtUtils.generateTokenFromUserDetailsImplt(userAuthenticate);

            AcessDTO acessDTO = new AcessDTO(token);

            return acessDTO;

        }catch (BadCredentialsException e) {
            //TODO LOGIN OU SENHA INVALIDO
        }
        return null;
    }
}
