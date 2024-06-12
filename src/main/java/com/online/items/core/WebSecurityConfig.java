package com.online.items.core;

import com.online.items.core.domain.Role;
import com.online.items.core.security.CustomMongoSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    public CustomMongoSecurityService mongoSecurityService;

    //@Autowired
    //public CustomAuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/continue", "/error", "/img/**", "/css/**", "/fonts/**", "/js/**", "/scss/**").permitAll()
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
                .formLogin((form) -> form
                        .loginPage("/auth/login").permitAll()
                        .defaultSuccessUrl("/auth/success")
                        .failureForwardUrl("/auth/login?error")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login")
                )
                .logout((logout) -> {
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"));
                    logout.invalidateHttpSession(true);
                    logout.clearAuthentication(true);
                    logout.deleteCookies("JSESSIONID");
                    logout.logoutSuccessUrl("/auth/login");
                });

        return http.build();
    }

    //@Autowired
    //public void configureGlobal(AuthenticationManagerBuilder auth) {
    //    auth.authenticationProvider(authenticationProvider);
    //}

    /*@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(mongoSecurityService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }*/

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(mongoSecurityService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}