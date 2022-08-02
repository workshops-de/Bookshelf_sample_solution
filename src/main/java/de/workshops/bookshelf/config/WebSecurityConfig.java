package de.workshops.bookshelf.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
<<<<<<< HEAD
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
=======
>>>>>>> Add_more_credentials
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JdbcTemplate jdbcTemplate;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                authorize ->
                        authorize
                                .antMatchers("/h2-console/**").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .headers().frameOptions().disable().and()
                .build();
    }

    @Bean
<<<<<<< HEAD
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/h2-console/**");
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
=======
    UserDetailsService userDetailsService() {
        var user = User.builder().username("user").password("password").roles("USER").build();
        var admin = User.builder().username("admin").password("admin").roles("USER", "ADMIN").build();
        return new InMemoryUserDetailsManager(user, admin);
>>>>>>> Add_more_credentials
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
