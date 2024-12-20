package com.example.kadai_002.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                // 静的リソースや一般公開ページは許可
                .requestMatchers("/css/**", "/images/**", "/js/**", "/storage/**", "/", "/signup/**", "/houses", "/houses/{id}", "/stripe/webhook", "/reset").permitAll()
                
                // レビュー投稿ページとその関連機能を ROLE_PRIME に限定
                .requestMatchers("/houses/**/reviews/register", "/houses/**/reviews/create").hasAuthority("ROLE_PRIME")

                // 管理者のみアクセス可能
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

                // その他のページは認証を要求
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")               // ログインページ
                .loginProcessingUrl("/login")      // ログインフォームの送信先
                .defaultSuccessUrl("/?loggedIn")   // ログイン成功時のリダイレクト先
                .failureUrl("/login?error")        // ログイン失敗時のリダイレクト先
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutSuccessUrl("/?loggedOut")   // ログアウト時のリダイレクト先
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}