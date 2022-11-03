package de.workshops.bookshelf.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Value("${spring.h2.console.enabled}")
    private boolean h2ConsoleEnabled;

    private final JdbcTemplate jdbcTemplate;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return configureHttpSecurity(httpSecurity).build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            String sql = "SELECT * FROM bookshelf_user WHERE username = ?";

            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    Collections.singletonList(
                            new SimpleGrantedAuthority(rs.getString("role"))
                    )
            ), username);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private HttpSecurity configureHttpSecurity(HttpSecurity httpSecurity) throws Exception {
        HttpSecurity customizedHttpSecurity = httpSecurity
                .authorizeHttpRequests(
                        authorize -> {
                            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry
                                    authorizationManagerRequestMatcherRegistry = authorize
                                    .requestMatchers("/actuator/**").permitAll();

                            // This is necessary to omit the path for the H2 console during test runs (where
                            // H2ConsoleProperties won't be available as a bean).
                            if (h2ConsoleEnabled) {
                                authorizationManagerRequestMatcherRegistry
                                        // "/h2-console/**" unfortunately doesn't suffice here anymore, because
                                        // H2ConsoleAutoConfiguration adds additional properties using an
                                        // H2ConsoleRequestMatcher
                                        .requestMatchers(PathRequest.toH2Console()).permitAll();
                            }

                            authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
                        }
                )
                .httpBasic(withDefaults())
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.successHandler(
                        (request, response, authentication) -> {
                            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                            jdbcTemplate.update(
                                    "UPDATE bookshelf_user SET lastlogin = NOW() WHERE username = ?",
                                    userDetails.getUsername()
                            );
                            response.sendRedirect("/success");
                        }
                ))
                .headers().frameOptions().disable().and();

        // This is necessary to omit the CSRF token check for the H2 console during test runs (where H2ConsoleProperties
        // won't be available as a bean).
        if (h2ConsoleEnabled) {
            customizedHttpSecurity.csrf().ignoringRequestMatchers(PathRequest.toH2Console());
        }

        return customizedHttpSecurity;
    }
}
