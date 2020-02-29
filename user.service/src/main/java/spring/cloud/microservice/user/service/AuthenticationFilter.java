package spring.cloud.microservice.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Log4j2
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    public AuthenticationFilter(UserService userService,
                                AuthenticationManager authenticationManager) {
        this.userService = userService;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws AuthenticationException {
        try {

            LoginDto credentials = new ObjectMapper().readValue(req.getInputStream(), LoginDto.class);

            return getAuthenticationManager()
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            credentials.getEmail(),
                            credentials.getPassword(),
                            new ArrayList<>()));


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse resp,
            FilterChain chain,
            Authentication authentication
    ) throws IOException, ServletException {

        String username = ((User) authentication.getPrincipal()).getUsername();
        UserDto userDetails = userService.getUserDetailsByEmail(username);

        String token = Jwts.builder()
                .setSubject(userDetails.getId().toString())
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60_1000)) // jwt expiry time
                .signWith(SignatureAlgorithm.HS512, "123456787654321") // jwt secret key
                .compact();

        resp.addHeader("token", token);
        resp.addHeader("userId", userDetails.getId().toString());

    }
}
