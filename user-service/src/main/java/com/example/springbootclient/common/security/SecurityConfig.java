package com.example.springbootclient.common.security;

import com.example.springbootclient.common.utils.CookieProvider;
import com.example.springbootclient.common.utils.JwtProvider;
import com.example.springbootclient.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authConfiguration;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final CookieProvider cookieProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        LoginAuthenticationFilter loginAuthenticationFilter =
            new LoginAuthenticationFilter(authenticationManager(authConfiguration), jwtProvider, cookieProvider);
        loginAuthenticationFilter.setFilterProcessesUrl("/login");

        http
            .httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authorizeRequests((authorize) -> authorize
                .requestMatchers( "/api/v1/login2").permitAll()
                .anyRequest().authenticated())
//            .formLogin(formLogin -> formLogin
//                .loginPage("/api/v1/login")
//                .defaultSuccessUrl("/home"))
//            .logout((logout) -> logout
//                .logoutSuccessUrl("/api/v1/login")
//                .invalidateHttpSession(true))
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilter(loginAuthenticationFilter)
            .addFilterBefore(new JwtFilter(userService, jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

}
