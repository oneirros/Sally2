package com.oneirros.sally2.config;

import com.oneirros.sally2.filter.JwtTokenFilter;
import com.oneirros.sally2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.oneirros.sally2.entity.UserRole.ROLE_ADMIN;
import static com.oneirros.sally2.entity.UserRole.ROLE_USER;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenFilter jwtTokenFilter;
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(JwtTokenFilter jwtTokenFilter, UserDetailsService userDetailsService) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService); //.passwordEncoder(passwordEncoder())
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors();
        http.httpBasic().and().authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.POST, "/user", "/trip")
                    .hasAnyRole(ROLE_USER.getRoleName(), ROLE_ADMIN.getRoleName())
                .antMatchers(HttpMethod.GET, "/user/*", "/trip/*", "/trip/allTrips/*", "/day/allDays/*")
                    .hasAnyRole(ROLE_USER.getRoleName(), ROLE_ADMIN.getRoleName())
                .antMatchers(HttpMethod.PATCH, "/user/*/password", "/trip/*/name")
                    .hasAnyRole(ROLE_USER.getRoleName(), ROLE_ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, "/trip/*")
                    .hasAnyRole(ROLE_USER.getRoleName())
                .antMatchers(HttpMethod.DELETE, "/user/*")
                    .hasAnyRole(ROLE_ADMIN.getRoleName())
                .and().csrf().disable()
                .logout().permitAll();

        http.addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Używamy tego w klasie AuthController
    @Override @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}