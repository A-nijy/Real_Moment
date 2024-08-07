package rm.backend.jwt.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;
import rm.backend.exception.CustomAccessDeniedHandler;
import rm.backend.jwt.config.auth.CustomUserDetailsService;
import rm.backend.jwt.config.filter.CustomJwtFilter;
import rm.backend.jwt.config.util.JWTProperties;
import rm.backend.jwt.config.util.JwtFunction;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtFunction jwtFunction;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JWTProperties jwtProperties;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
        sharedObject.userDetailsService(this.customUserDetailsService);
        AuthenticationManager authenticationManager = sharedObject.build();
        http.authenticationManager(authenticationManager);

        http.csrf(cs -> cs.disable());
//        http.addFilter(corsFilter);

        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.formLogin(f -> f.disable());
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.addFilterBefore(new CustomJwtFilter(customUserDetailsService, jwtFunction, jwtProperties), UsernamePasswordAuthenticationFilter.class);


        http.authorizeHttpRequests(authorize -> {
            authorize
                    // 공통 관리자
                    .requestMatchers(new AntPathRequestMatcher("/api/admin/**/view")).hasAnyRole("REPRESENTATIVE", "OPERATOR", "CUSTOMER", "ADMIN")
                    // 대표
                    .requestMatchers("/api/adminIdCheck",
                            "/api/adminJoin",
                            "/api/admin/admin/List",
                            "/api/admin/admin/**",
                            "/api/admin/grade/**")
                    .hasAnyRole("REPRESENTATIVE")
                    // 운영 관리자
                    .requestMatchers("/api/admin/order/**",
                            "/api/admin/item/**",
                            "/api/admin/category/**")
                    .hasAnyRole("OPERATOR", "REPRESENTATIVE")
                    // 고객 관리자
                    .requestMatchers("/api/admin/*/announcement/**",
                            "/api/admin/*/QAComment",
                            "/api/admin/*/comment")
                    .hasAnyRole("CUSTOMER", "REPRESENTATIVE")
                    // 회원
                    .requestMatchers("/api/member/**")
                    .hasAnyRole("USER")
                    .anyRequest().permitAll();}
        );

        http.exceptionHandling(exception -> exception.accessDeniedHandler(customAccessDeniedHandler));

        // Spring Security와 통합된 CORS 설정
        http.cors(withDefaults());

        return http.build();
    }
}
