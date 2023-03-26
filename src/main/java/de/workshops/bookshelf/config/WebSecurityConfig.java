package de.workshops.bookshelf.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JdbcTemplate jdbcTemplate;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // see https://docs.spring.io/spring-security/reference/5.8/migration/servlet/exploits.html,
        // https://docs.spring.io/spring-security/reference/5.8/migration/servlet/exploits.html#_i_am_using_angularjs_or_another_javascript_framework,
        // and https://github.com/spring-projects/spring-security/issues/12915#issuecomment-1482669321
        CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        // Please note: This disables CSRF BREACH protection.
        // https://blog.gypsyengineer.com/en/security/csrf-tokens-in-spring-and-the-breach-attack.html
        // https://security.stackexchange.com/questions/43669/with-breach-attack-is-session-based-csrf-token-still-secure
        // See this more elaborate implementation for both use cases (JavaScript and default login form) to work
        // and still including CSRF BREACH protection: https://github.com/spring-projects/spring-security/issues/12915#issuecomment-1482931700
        CsrfTokenRequestAttributeHandler delegate = new CsrfTokenRequestAttributeHandler();

        return http
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/actuator/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/book/**").permitAll()
                                .anyRequest().authenticated()
                )
                .csrf(
                        csrf -> csrf
                                .csrfTokenRepository(tokenRepository)
                                .csrfTokenRequestHandler(delegate::handle) // delegate::handle is required here to ensure proper CSRF token handling
                )
                .cors(withDefaults())
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
                .build();
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
}
