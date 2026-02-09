package com.hellFire.QuizGame.config;

import com.hellFire.QuizGame.dto.request.SignUpRequest;
import com.hellFire.QuizGame.entity.User;
import com.hellFire.QuizGame.repositories.IUserRepository;
import com.hellFire.QuizGame.services.IUserService;
import com.hellFire.QuizGame.services.impl.UserService;
import com.hellFire.QuizGame.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IUserRepository userRepository;

    private final IUserService userService;

    public OAuth2SuccessHandler(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        User user = userRepository.findByEmail(email);

        if(Objects.isNull(user)){
            SignUpRequest signUpRequest = new SignUpRequest();
            signUpRequest.setEmail(email);
            signUpRequest.setProvider("GOOGLE");;
            user = userService.createUser(signUpRequest);
        }

        String token = jwtUtil.generateToken(email);

        response.sendRedirect("http://localhost:3000/oauth-success?token=" + token);
    }
}

