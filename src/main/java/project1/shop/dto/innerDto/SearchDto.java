package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class SearchDto {



    // 상품 목록 조회
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Items{
        private String itemSort = null;                // 정렬 = new, hot, sale
        private Long categoryId = null;                // 카테고리
        private String itemName = null;                // 상품 이름
        private Boolean isDeleteCheck = null;           // 상품 삭제 여부
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


    // 상품 문의 목록 조회
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ItemQAs{
        private Long itemId = null;                      // 해당 상품
        private Boolean answer = null;                 // 답변 여부
        private int nowPage = 1;                // 페이지
    }


    // 주문 내역 목록 조회
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Orders{
        private String status = null;                  // 주문 상태 - 결제완료, 출고준비, 출고완료, 배송준비, 주문취소 등등
        private int nowPage = 1;                // 페이지
    }


    // 회원 목록 조회
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Members{
        private String loginId = null;                    // 특정 회원 아이디
        private Long gradeId = null;                       // 등급
        private Boolean isDelete = null;               // 탈퇴 여부
        private int nowPage = 1;                // 페이지
    }


    // 페이지만 조회 (공지사항, 내가 작성한 리뷰, 문의 등)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Page {
        private int nowPage = 1;                // 페이지
    }
}
