package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

public class SearchDto {


    // 상품 목록 조회
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Items{
        private String itemSort = "new";                // 정렬 = new, sell, low, high, sale
        private Long categoryId = null;                // 카테고리
        private String itemName = null;                // 상품 이름
        private int nowPage = 1;                       // 페이지
    }


    // 상품 목록 조회 (관리자)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class AdminItems{
        private String itemSort = "new";                // 정렬 = new, sell, low, high, sale
        private Long categoryId = null;                // 카테고리
        private String itemName = null;                // 상품 이름
        private Boolean isSell = null;                   // 상품 판매 여부
        private Boolean isDelete = false;                // 상품 삭제 여부
        private int nowPage = 1;                       // 페이지
    }


    // 리뷰 목록 조회
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Reviews {
        private Long itemId = null;                    // 해당 상품
        private Integer star = null;                       // 특정 별점
        private int nowPage = 1;                        // 페이지
    }



    // 상품 문의 목록 조회 (관리자)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class AdminItemQA {
        private Long itemId = null;                      // 해당 상품
        private Boolean isAnswer = null;                 // 답변 여부
        private int nowPage = 1;                // 페이지
    }


    // 상품 문의 목록 조회 (상품 상세)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ItemInItemQA{
        private Long itemId = null;                      // 해당 상품
        private int nowPage = 1;                // 페이지
    }


    // 1대1 문의 목록 조회 (내꺼)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class OneOnOnes{
        private Boolean isAnswer = null;
        private int nowPage = 1;
    }


    // 1대1 문의 목록 조회 (관리자)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class OneOnOneSearch{
        private String loginId = null;
        private Boolean isAnswer = null;
        private int nowPage = 1;
    }


    // 나의 주문 내역 목록 조회
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class MyOrdersSearch {
        private String itemName = null;
        private LocalDate startDate = null;
        private LocalDate lastDate = null;
//        @Nullable
//        private PaymentStatus status;                  // 주문 상태 - 결제완료, 출고준비, 출고완료, 배송준비, 주문취소 등등
        private String status = null;
        private int nowPage = 1;                // 페이지
    }


    // 주문 내역 목록 조회 (관리자용)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class OrdersSearch{
        private String itemName = null;
        private String loginId = null;
        private String merchantUid = null;
        private LocalDate startDate = null;
        private LocalDate lastDate = null;
//        private PaymentStatus status = null;                  // 주문 상태 - 결제완료, 출고준비, 출고완료, 배송준비, 주문취소 등등
        private String status = null;
        private int nowPage = 1;                // 페이지
    }


    // 회원 목록 조회
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Members{
        private String memberSort = null;                   // 회원 정렬 - day 가입일, totalPay 올해 총 구매 금액
        private String loginId = null;                    // 특정 회원 아이디
        private Long gradeId = null;                       // 등급
        private Boolean isDelete = null;               // 탈퇴 여부
        private int nowPage = 1;                // 페이지
    }


    // 관리자 목록 조회
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Admins{
        private String loginId = null;                  // 관리자 아이디
        private String name = null;                     // 관리자 이름
        private String roles = null;                    // 관리자 권한
        private int nowPage = 1;                        // 페이지
    }


    // 페이지만 조회 (공지사항, 내가 작성한 리뷰, 문의, 포인트 내역 등)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Page {
        private int nowPage = 1;                // 페이지
    }


}
