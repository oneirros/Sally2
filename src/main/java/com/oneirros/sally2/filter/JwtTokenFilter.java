package com.oneirros.sally2.filter;

import com.oneirros.sally2.entity.UserEntity;
import com.oneirros.sally2.pojo.SecurityUserDetailsImpl;
import com.oneirros.sally2.repository.UserRepository;
import com.oneirros.sally2.util.JwtTokenUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil,
                          UserRepository userRepository  //UserDetailsServiceImpl
    ) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        // Wyciągamy nagłówek
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
//            throw new ServletException("Wrong or empty header");
        }

        // Z nagłówka wyciągamy token
        final String token = header.split(" ")[1].trim();
        if (!jwtTokenUtil.verifyToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        final String userEmail;

        userEmail = jwtTokenUtil.getUserEmail(token);

        Optional<UserEntity> optionalPrincipal = this.userRepository.findByEmail(userEmail);

        if (!optionalPrincipal.isPresent()) {
            chain.doFilter(request, response);
            return;
//            throw new ServletException("User not founded");
        }

        UserEntity principal = optionalPrincipal.get();
        UserDetails userDetails = new SecurityUserDetailsImpl(principal.getRole(),
                principal.getPassword(),
                principal.getEmail());


        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
            );


        authentication.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );

        // Pakujemy tutaj dane do SecurityContextHolder które przechowuje dane o zalogowanym użytkowniku
        // Jakby tutaj użytkownik zostaje zalogowany
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

}