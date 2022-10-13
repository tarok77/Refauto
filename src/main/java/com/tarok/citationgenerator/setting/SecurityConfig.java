package com.tarok.citationgenerator.setting;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 当初はログイン機能をもつサイトにしようとして入れたが現在は実装するかどうかは未定、CSRF対策としては機能している。。
 */
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
                            .logoutSuccessUrl("/")
                    ).authorizeHttpRequests(authz -> authz
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            .mvcMatchers("/","/login","/about","/caution","/submit/isbn",
                                    "/submit/title","/book/confirmed","/article",
                                    "/article/submit","/article/confirmed").permitAll()
                            .anyRequest().authenticated()
                    )//.headers().frameOptions().disable().and().cors().and().csrf().disable()//開発環境用に残している。
            ;
            return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
