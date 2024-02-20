package project1.shop.enumeration;

// 주문 상태


public enum PaymentStatus {

    PAYMENT_READY("결제준비"),              // 결제 준비
    PAYMENT_DONE("결제완료"),               // 결제 완료
    DELIVERY_READY("배송준비"),             // 배송 준비
    DELIVERY_DOING("배송중"),              // 배송 중
    DELIVERY_DONE("배송완료"),              // 배송 완료
    CANCEL("결제취소"),                     // 결제 취소
    REFUND_REQUEST("환불요청"),             // 환불 요청
    REFUND_DONE("환불완료")                 // 환불 완료
    ;


    // 문자열을 저장할 필드
    private String paymentStatus;

    // 생성자 (싱글톤)
    private PaymentStatus(String paymentStatus){
        this.paymentStatus = paymentStatus;
    }

    // Getter
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /*
    PaymentStatus paymentStatus = PaymentStatus.REFUND_DONE;

    paymentStatus.name()                - 열거 객체명 출력 : REFUND_DONE
    paymentStatus.getPaymentStatus()    - 매핑된 열거 데이터 출력 : 환불완료
     */
}
