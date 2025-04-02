package com.learn.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurtiyFilter {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;

    public SecurtiyFilter(
            JwtAuthFilter jwtAuthFilter,
            AuthenticationProvider authenticationProvider //ignore Bean warning we will get to that
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()) //disable csrf mode
                .authorizeHttpRequests(
                        authorize-> authorize.requestMatchers("/auth/**").permitAll() //allows register without authentication
                        .anyRequest().authenticated()
                )  //authenticates all request
//              .formLogin(Customizer.withDefaults()) //enables form login form but not required for restAPI
                .httpBasic(Customizer.withDefaults()) // for RestAPIs otherwise will return HTML
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // makes request stateless
                .authenticationProvider(authenticationProvider) // custom authentication like username validation etc
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // calls jwtFilter before UsernamePasswordAuthenticationFilter
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080")); //TODO: update backend url
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
