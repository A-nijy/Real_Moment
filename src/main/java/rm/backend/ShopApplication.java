package rm.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class ShopApplication {


	// 비밀번호를 암호화하여 데이터베이스에 저장하기 위해 BCryptPasswordEncoder 인스턴스를 빈으로 등록
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	// @CreateDate, @CreateBy, @LastModifiedDate, @LastModifiedBy를 사용하기 위해 AuditorAware 인터페이스를 빈으로 등록
	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of(UUID.randomUUID().toString());
	}

}
