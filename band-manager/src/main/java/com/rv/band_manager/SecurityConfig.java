package com.rv.band_manager;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.rv.band_manager.Model.Role;
import com.rv.band_manager.Service.CustomerUserDetailsService;

/**
 * Security configuration to handle authentication and authorization
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomerUserDetailsService userDetailsService;

    /**
     * constructor for SecurityConfig
     *
     * @param userDetailsService service used for user details
     */
    public SecurityConfig(CustomerUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configures the security filter chain for the application's authentication and authorization
     *
     * @param http the HttpSecurity to configure security settings
     * @return a configured SecurityFilterChain for Spring Security
     * @throws Exception if an error occurs during security configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/register", "/login").permitAll()
                        .requestMatchers("/performance").authenticated()
                        .requestMatchers("/director/**").hasRole(Role.DIRECTOR.name())
                        .requestMatchers("/committee-member/**").hasRole(Role.COMMITTEE_MEMBER.name())
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginProcessingUrl("/login")
                        .loginPage("/login")
                        .failureHandler(authenticationFailureHandler())
                        .successHandler(authenticationSuccessHandler())
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                );
        return http.build();
    }

    /**
     * Password encode bean
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Expose AuthenticationFailureHandler bean
     *
     * @return authentication failure handler
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }

    /**
     * Expose AuthenticationSuccessHandler bean
     *
     * @return authentication success handler
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new CustomAuthenticationSuccessHandler();
    }

    /**
     * Expose AuthenticationManager bean
     *
     * @param http the HttpSecurity used to obtain the shared AuthenticationManagerBuilder
     * @return a configured AuthenticationManager for handling user authentication
     * @throws Exception if an error occurs during authentication manager configuration
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
