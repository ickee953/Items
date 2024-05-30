package com.online.items.core;

import com.online.items.core.domain.Role;
import com.online.items.core.service.CustomMongoSecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/img/**", "/css/**", "/fonts/**", "/js/**", "/scss/**").permitAll()
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
                        .loginPage("/auth/login").permitAll()
                        .defaultSuccessUrl("/auth/success")
                        .failureForwardUrl("/auth/login?error")
                )
                .logout((logout) -> {
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"));
                    logout.invalidateHttpSession(true);
                    logout.clearAuthentication(true);
                    logout.deleteCookies("JSESSIONID");
                    logout.logoutSuccessUrl("/auth/login");
                });

        return http.build();
    }

    @Bean
    public UserDetailsService getUserDetailsService() {
        return new CustomMongoSecurityService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

}
