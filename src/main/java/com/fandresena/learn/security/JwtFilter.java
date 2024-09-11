package com.fandresena.learn.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fandresena.learn.dao.SuperUserDAO;
import com.fandresena.learn.dao.UserDAO;
import com.fandresena.learn.model.SuperUserModel;
import com.fandresena.learn.model.UserModel;
import com.fandresena.learn.service.JWTService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    // private AccessTokenService accessTokenService;
    private JWTService accessTokenService;
    private UserDAO userDAO;
    private SuperUserDAO superUserDAO;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // list of the path we don't want to filter
        String[] filterList = { "/createPassword","/login", "/access-token","/superuser/login"};

        if (List.of(filterList).contains(request.getServletPath())) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header != null || header.startsWith("Bearer ")) {
            final String token = header.substring(7).trim();
            try {
                UserModel user = userDAO.findByEmail(accessTokenService.extractUserEmail(token));
                SuperUserModel superUser = null;

                if(user==null){
                    superUser = superUserDAO.findByEmail(accessTokenService.extractUserEmail(token));
                    if(superUser==null) throw new IllegalStateException("SuperUser not found");
                }

                if ((user != null || superUser !=null) && accessTokenService.isTokenValid(token, user!=null?user:superUser)) {
                    // if token expired , Throw error 401 error
                    if (accessTokenService.isTokenExpired(token)) {
                        throw new ExpiredJwtException(null, null, token);
                    } else {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                //if user is null, pass to superUser
                                user!=null?user.getEmail():superUser.getEmail(), null,  user!=null?user.getAuthorities():superUser.getAuthorities());

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
                else throw new IllegalStateException("User not found");

            } catch (ExpiredJwtException e) {
                throw e;
            } catch (MalformedJwtException | UnsupportedJwtException e) {
                throw e;
            } catch (IllegalArgumentException e) {
                throw e;

            } catch (Exception e) {
                throw e;
            }
        }
        chain.doFilter(request, response);
    }
}
