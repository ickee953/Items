package com.online.items.core;

import com.online.items.core.domain.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/css/**", "/fonts/**", "/js/**", "/scss/**").permitAll()
                        .requestMatchers("/header").hasAnyRole(Role.ROLE_ADMINISTRATOR, Role.ROLE_USER)
                        .requestMatchers("/footer").hasAnyRole(Role.ROLE_ADMINISTRATOR, Role.ROLE_USER)
                        .requestMatchers("/users/**").hasAnyRole(Role.ROLE_ADMINISTRATOR, Role.ROLE_USER)
                        .requestMatchers("/items/**").hasAnyRole(Role.ROLE_ADMINISTRATOR, Role.ROLE_USER)
                        .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAnyRole(Role.ROLE_ADMINISTRATOR, Role.ROLE_USER)
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").hasRole(Role.ROLE_ADMINISTRATOR)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/*").hasRole(Role.ROLE_ADMINISTRATOR)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/*").hasRole(Role.ROLE_ADMINISTRATOR)
                        .requestMatchers(HttpMethod.GET, "/api/v1/items").hasAnyRole(Role.ROLE_ADMINISTRATOR, Role.ROLE_USER)
                        .requestMatchers(HttpMethod.POST, "/api/v1/items").hasAnyRole(Role.ROLE_ADMINISTRATOR, Role.ROLE_USER)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/items/*").hasAnyRole(Role.ROLE_ADMINISTRATOR, Role.ROLE_USER)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/items/*").hasAnyRole(Role.ROLE_ADMINISTRATOR, Role.ROLE_USER)
                        .anyRequest()
                        .authenticated()
                )
                .userDetailsService(getUserDetailsService())
                .formLogin((form) -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/success")
                        .failureForwardUrl("/login?error")
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public UserDetailsService getUserDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("123"))
                        .roles(Role.ROLE_ADMINISTRATOR)
                        .build(),
                User.builder()
                        .username("user")
                        .password(passwordEncoder().encode("password"))
                        .roles(Role.ROLE_USER)
                        .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

}
