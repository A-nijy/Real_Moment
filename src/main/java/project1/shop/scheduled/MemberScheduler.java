package project1.shop.scheduled;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Member;
import project1.shop.domain.repository.MemberRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberScheduler {

    private final MemberRepository memberRepository;


    // 매년 회원의 총 구매 금액 초기화 스케줄러 (매년 초마다 회원의 올해 총 구매 금액 값 0으로 초기화)
    @Scheduled(cron = "0 0 0 1 1 ?")
    @Transactional
    public void updateMemberTotalPay(){

        List<Member> memberList = memberRepository.findThisYearPayMember();

        for (Member member : memberList){

            member.resetThisYearPay();
        }
    }
}
