package com.example.livapp.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;

//Tells Spring that this class contains bean definitions (methods annotated with @Bean).
//Spring Boot will automatically detect and process it during startup.
@Configuration
//Enables Spring Security’s filter chain for web requests
// Looks for your SecurityFilterChain bean(s) and applies them.
// Replaces the default Spring Boot security configuration with your custom rules.
@EnableWebSecurity
//Enables method-level security annotations, such as:
//@PreAuthorize
@EnableMethodSecurity
public class WebSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;


    @Autowired
    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/api/customer/**",
                                "/actuator/health"
                        ).permitAll()
                        .requestMatchers("order/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // Custom logout URL
                        .logoutSuccessUrl("/login?logout=true")  // Redirect after logout
                        .deleteCookies("JSESSIONID","remember-me")
                        .invalidateHttpSession(true) //It destroys the HTTP session on the server side (session id,....)
                        .clearAuthentication(true) //Removes the authentication information (the SecurityContext) from memory, user’s identity.
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );
               /* .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .expiredUrl("/login?expired")
                        .sessionRegistry(sessionRegistry())
                        .and()
                        .invalidSessionUrl("/login?invalid")
                ); */

        return http.build();
    }
/*
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
*/





   /* @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }*/

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


}