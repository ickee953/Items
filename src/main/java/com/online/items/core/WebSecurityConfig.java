package com.online.items.core;

import com.online.items.core.domain.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/items", "/css/**", "/fonts/**", "/js/**", "/scss/**").permitAll()
                        .requestMatchers("/users").hasAnyRole(Role.ROLE_ADMINISTRATOR, Role.ROLE_USER)
                        .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAnyRole(Role.ROLE_ADMINISTRATOR, Role.ROLE_USER)
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").hasRole(Role.ROLE_ADMINISTRATOR)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/*").hasRole(Role.ROLE_ADMINISTRATOR)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/*").hasRole(Role.ROLE_ADMINISTRATOR)
                        .requestMatchers(HttpMethod.GET, "/api/v1/items").hasAnyRole(Role.ROLE_ADMINISTRATOR, Role.ROLE_USER)
                        .requestMatchers(HttpMethod.POST, "/api/v1/items").hasAnyRole(Role.ROLE_ADMINISTRATOR, Role.ROLE_USER)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/items/*").hasAnyRole(Role.ROLE_ADMINISTRATOR, Role.ROLE_USER)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/items/*").hasAnyRole(Role.ROLE_ADMINISTRATOR, Role.ROLE_USER)
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("password")
                        .roles("ROLE_ADMINISTRATOR")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("ROLE_USER")
                        .build()
        );
    }

}
