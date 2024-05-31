package project1.shop.smtp;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EmailScheduler {

    private final EmailCodeRepository emailCodeRepository;

    @Scheduled(fixedRate = 3600000)  // 1시간마다 실행
    public void cleanUpExpiredCodes() {
        emailCodeRepository.deleteByExpiryDateBefore(LocalDateTime.now());
    }
}
