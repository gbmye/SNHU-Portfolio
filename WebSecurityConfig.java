package com.example.relational_data_access;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/**").hasRole("USER")
                )
                .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // remember the passwords that are printed out and use in the next step.
        // PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // System.out.println(encoder.encode("userPassword"));
        // System.out.println(encoder.encode("adminPassword"));

        // for clarity, the password is "userPassword"
        UserDetails user = User.withUsername("user")
                .password("{bcrypt}$2a$10$0pqELfmNAL.HpoTgV7RsN.OJ/vgH6eDq4stK6yBkS12Y0zehMJBkO")
                .roles("USER")
                .build();
        // for clarity, the password is "adminPassword"
        UserDetails admin = User.withUsername("admin")
                .password("{bcrypt}$2a$10$SgKM2pCS5M9U0.FWizH0je.YhBVozmVbwSeCArtW/33R2OgepYPFi")
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}