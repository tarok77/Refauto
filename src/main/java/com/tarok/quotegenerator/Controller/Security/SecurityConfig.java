package com.tarok.quotegenerator.Controller.Security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .formLogin(login -> login
                            .loginProcessingUrl("/login")
                            //.loginPage("/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/",true)
                            //.failureUrl("")
                            .permitAll()

                    ).logout(logout -> logout
                            //要実装
                            .logoutSuccessUrl("/")
                    ).authorizeHttpRequests(authz -> authz
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            .antMatchers("/h2-console/**").permitAll()
                            .mvcMatchers("/","/login","/submit/isbn","/submit/title").permitAll()
                            .mvcMatchers().hasRole("USER")
                            .anyRequest().authenticated()
                    ).headers().frameOptions().disable().and().cors().and().csrf().disable()//本番環境では取る。
            ;
            return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
        @Bean
        public InMemoryUserDetailsManager userdetailsservice() {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            UserDetails user = User
                    .withUsername("tes")
                    .password(passwordEncoder.encode("pass"))
                    .roles("USER")
                    .build();
            return new InMemoryUserDetailsManager(user);
        }
    }
