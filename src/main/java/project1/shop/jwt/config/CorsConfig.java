package project1.shop.jwt.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);                                                            // 내 서버가 응답할 때 JSON을 js에서 처리할 수 있게 할지를 설정
        config.addAllowedOriginPattern("*");                                                                // 모든 ip에 응답을 허용
        config.addAllowedHeader("*");                                                                // 모든 header에 응답을 허용
        config.addAllowedMethod("*");                                                                // 모든 http Method (post, get 등등)의 요청을 허용

        config.addExposedHeader("Authorization");
        config.addExposedHeader("Refresh_Token");

        source.registerCorsConfiguration("/**", config);                                     // /api/**로 들어오는 url에 대해서는 config대로 정의함

        System.out.println("Cors Filter 수행 완료");

        return new CorsFilter(source);
    }
}


//      아래꺼로 리액트와 연동 잘됨 내가 원래 작성한 위에 코드는 연결 안됨
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOriginPattern("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }