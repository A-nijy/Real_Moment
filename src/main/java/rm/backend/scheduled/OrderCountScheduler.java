package rm.backend.scheduled;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rm.backend.component.FieldData;
import rm.backend.domain.entity.Member;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderCountScheduler {

    private final FieldData fieldData;

    // 매년 회원의 총 구매 금액 초기화 스케줄러 (매년 초마다 회원의 올해 총 구매 금액 값 0으로 초기화)
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateMemberTotalPay(){

        fieldData.resetCount();
    }
}
