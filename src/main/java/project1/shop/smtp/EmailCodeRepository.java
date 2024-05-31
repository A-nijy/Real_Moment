package project1.shop.smtp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailCodeRepository extends JpaRepository<EmailCode, String> {

    Optional<EmailCode> findByEmail(String email);

    // 주어진 시간 (현재시각을 고정적으로 보낼 예정) 보다 이전 시간인 데이터들은 삭제
    void deleteByExpiryDateBefore(LocalDateTime now);
}
