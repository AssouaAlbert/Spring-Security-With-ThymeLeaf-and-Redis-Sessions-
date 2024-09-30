package com.assoua.thyme_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/home", "/news", "/", "/captcha", "/user-login").permitAll()  // Allow public access to /home and /news
                        .requestMatchers("/profile", "/logout").authenticated() // Require authentication for /profile
                        .anyRequest().authenticated()  // All other requests require authentication
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Use custom login page
                        // Remove to prevent spring from automatically handling the authentication
                        .loginProcessingUrl("/login")  // URL that Spring Security will use to process the login
                        .defaultSuccessUrl("/home", true)  // Redirect to /home after successful login
                        .failureUrl("/login?error=true")  // Redirect to /custom-login with error if authentication fails
                        .permitAll()  // Allow everyone to access the login page
                ).logout(logout -> logout
                        .logoutUrl("/logout")  // URL to trigger logout
                        .logoutSuccessUrl("/login?logout")  // Redirect to login page after logout
                        .invalidateHttpSession(true)  // Invalidate session
                        .clearAuthentication(true)  // Clear authentication
                        .deleteCookies("JSESSIONID")  // Optionally delete cookies
                        .permitAll()  // Allow everyone to log out
                );

        return http.build();


    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                        .username("u")
                        .password("u")
                        .roles("USER")
                        .build()
        );
    }
}
