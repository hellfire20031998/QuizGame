package com.hellFire.QuizGame.config;

import com.hellFire.QuizGame.dto.response.ApiResponse;
import com.hellFire.QuizGame.entity.User;
import com.hellFire.QuizGame.exceptions.ErrorCode;
import com.hellFire.QuizGame.repositories.IUserRepository;
import com.hellFire.QuizGame.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String auth = request.getHeader("Authorization");

        try {
            if (auth != null && auth.startsWith("Bearer ")) {

                String token = auth.substring(7);

                String email = jwtUtil.extractEmail(token);

                if (email != null) {
                    User user = userRepository.findByEmail(email);

                    if (user != null) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        user,
                                        null,
                                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                                );

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }

            filterChain.doFilter(request, response);
        } catch (io.jsonwebtoken.ExpiredJwtException ex) {
            writeErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                    ErrorCode.TOKEN_EXPIRED, "Token has expired. Please log in again.");
        } catch (io.jsonwebtoken.SignatureException ex) {
            writeErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                    ErrorCode.TOKEN_INVALID, "Invalid token signature.");
        } catch (Exception ex) {
            writeErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                    ErrorCode.TOKEN_MISSING, "Authentication failed: " + ex.getMessage());
        }
    }

    private void writeErrorResponse(HttpServletResponse response,
                                    int status,
                                    ErrorCode errorCode,
                                    String message) throws IOException {
        if (response.isCommitted()) {
            return;
        }

        response.setStatus(status);
        response.setContentType("application/json");

        ApiResponse<?> body = ApiResponse.error(errorCode.name(), message);
        String json = String.format(
                "{\"success\":false,\"errorCode\":\"%s\",\"message\":\"%s\"}",
                body.getErrorCode(),
                body.getMessage().replace("\"", "\\\"")
        );
        response.getWriter().write(json);
    }
}