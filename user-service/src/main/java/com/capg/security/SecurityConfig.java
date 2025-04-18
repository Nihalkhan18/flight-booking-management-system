package com.capg.security;

import com.capg.security.filter.JwtRequestFilter;
import com.capg.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        // Ignoring swagger paths
        webSecurity.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Enable CORS
        http.cors().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/authenticate", "/users/create")
                .permitAll()
                .antMatchers("/hello")
                .permitAll()
                .antMatchers("/users/getAll", "/users/delete/**", "/users/deleteAll")
                .hasRole("ADMIN")
                .antMatchers("/users/get/**", "/users/update/**")
                .hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();

        // Add JWT Filter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // It's better to use BCryptPasswordEncoder for better security
        return new BCryptPasswordEncoder();
    }

    // CORS Configuration Bean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:3000"); // Allow frontend origin
        configuration.addAllowedHeader("*");  // Allow all headers
        configuration.addAllowedMethod("*");  // Allow all methods (GET, POST, etc.)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply to all endpoints
        return source;
    }
}
