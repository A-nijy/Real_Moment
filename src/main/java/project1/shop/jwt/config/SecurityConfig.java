package project1.shop.jwt.config;


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
import project1.shop.exception.CustomAccessDeniedHandler;
import project1.shop.jwt.config.auth.CustomUserDetailsService;
import project1.shop.jwt.config.filter.CustomJwtFilter;
import project1.shop.jwt.config.util.JwtFunction;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtFunction jwtFunction;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
        sharedObject.userDetailsService(this.customUserDetailsService);
        AuthenticationManager authenticationManager = sharedObject.build();
        http.authenticationManager(authenticationManager);

        http.csrf(cs -> cs.disable());
        http.addFilter(corsFilter);

        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.formLogin(f -> f.disable());
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.addFilterBefore(new CustomJwtFilter(customUserDetailsService, jwtFunction), UsernamePasswordAuthenticationFilter.class);


        http.authorizeHttpRequests(authorize -> {
            authorize
                    // 공통 관리자
                    .requestMatchers(new AntPathRequestMatcher("/admin/**/view")).hasAnyRole("REPRESENTATIVE", "OPERATOR", "CUSTOMER", "ADMIN")
                    // 대표
                    .requestMatchers("/adminIdCheck",
                            "/adminJoin",
                            "/admin/admin/List",
                            "/admin/admin/**",
                            "/admin/grade/**")
                    .hasAnyRole("REPRESENTATIVE")
                    // 운영 관리자
                    .requestMatchers("/admin/order/**",
                            "/admin/item/**",
                            "/admin/category/**")
                    .hasAnyRole("OPERATOR", "REPRESENTATIVE")
                    // 고객 관리자
                    .requestMatchers("/admin/*/announcement/**",
                            "/admin/*/QAComment",
                            "/admin/*/comment")
                    .hasAnyRole("CUSTOMER", "REPRESENTATIVE")
                    // 회원
                    .requestMatchers("/member/**")
                    .hasAnyRole("USER")
                    .anyRequest().permitAll();}
        );

        http.exceptionHandling(exception -> exception.accessDeniedHandler(customAccessDeniedHandler));

        return http.build();
    }
}
