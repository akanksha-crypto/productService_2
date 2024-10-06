package com.example.productservice.authcommons;

import com.example.productservice.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
public class AuthenticationCommons {
    private RestTemplate restTemplate;
    public AuthenticationCommons(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public UserDto validateToken(String tokenvalue){
        UserDto userDto = restTemplate.getForObject("http://localhost:4141/users/validate/" + tokenvalue,
                UserDto.class);
        return userDto;
    }
}
