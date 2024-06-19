package project1.shop.scheduled;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.*;
import project1.shop.domain.repository.*;
import project1.shop.enumeration.PaymentStatus;
import project1.shop.enumeration.PointStatus;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderScheduler {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final GradeRepository gradeRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final PointRepository pointRepository;


    // 매일 자정에 실행되는 스케줄러 (배송 완료 이후 7일이 지난 status는 자동으로 구매 확정으로 변경)
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateOrderStatus() {

        // 배송 완료후 7일 이상 지난 order 가져오기
        List<Order> orderList = orderRepository.findSevenDays();

        for(Order order : orderList){
            // 주문 상태를 구매 확정으로 변경
            order.updateStatus(PaymentStatus.DONE);
            // 회원에 적립금, 올해 총 구매 금액 적용 및 등급 재정의
            StatusDoneSetting(order);
            // 상품 판매수량 업데이트
            List<OrderDetail> orderDetailList = orderDetailRepository.findByOrder(order);

            Member member = order.getMember();
            // 포인트 내역에 등록하기
            Point point = new Point(member, PointStatus.PURCHASE_DONE, "+"+order.getGetPoint());
            pointRepository.save(point);

            for (OrderDetail orderDetail : orderDetailList){

                Item item = orderDetail.getItem();

                item.plusSellCount(orderDetail.getItemCount());
                item.plusRevenue(orderDetail.getTotalPrice());
            }
        }
    }


    public void StatusDoneSetting(Order order) {

        // 회원에 적립금, 올해 총 구매 금액 적용
        Member member = memberRepository.findById(order.getMember().getMemberId()).orElseThrow(IllegalArgumentException::new);
        member.updateOrderMember(order);

        // 회원의 올해 총 구매 금액에 따라 등급 재정의
        Grade grade = gradeRepository.findHighestGrade(member.getThisYearPay());
        member.updateGrade(grade);
    }
}
