package spring.cloud.microservice.zuul.api.gateway.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

public class AutherizationFilter extends BasicAuthenticationFilter  {

    private final Environment environment;

    @Autowired
    public AutherizationFilter(AuthenticationManager authenticationManager, Environment environment) {
        super(authenticationManager);
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse resp,
            FilterChain chain
    ) throws IOException, ServletException {

        String authorizationHeader = req.getHeader("Authorization");

        if (null == authorizationHeader) {
            chain.doFilter(req, resp);
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, resp);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(
            HttpServletRequest req
    ) {
        String authorizationHeader = req.getHeader("Authorization");
        if (null == authorizationHeader) {
            return null;
        }

        String token = authorizationHeader.replace("Bearer", "");

        String userId = Jwts.parser()
                .setSigningKey("123456787654321")
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        if (null == userId)
            return null;


        return new  UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
    }

}
