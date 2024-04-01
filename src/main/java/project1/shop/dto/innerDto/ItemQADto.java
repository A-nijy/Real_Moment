package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.ItemQA;

import java.time.LocalDateTime;
import java.util.List;

public class ItemQADto {


    // 상품 문의 등록
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ItemQARequest {
        private Long itemId;
        private String title;
        private String content;
    }


    // 상품 문의 수정
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateItemQARequest {
        private Long itemQAId;
        private String title;
        private String content;
    }


    // 상품 문의 수정버튼 클릭시 해당 문의 데이터 반환
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateItemQAResponse {
        private Long itemQAId;
        private String title;
        private String content;


        public UpdateItemQAResponse(ItemQA itemQA){
            itemQAId = itemQA.getItemQAId();
            title = itemQA.getTitle();
            content = itemQA.getContent();
        }
    }


    // 상품 문의 목록 조회
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ItemQAResponse {
        private Long itemQAId;
        private String loginId;
        private String title;
        private String content;
        private boolean isAnswer;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        private QACommentDto.Response qaComment = null;


        public ItemQAResponse(ItemQA itemQA){
            itemQAId = itemQA.getItemQAId();
            loginId = itemQA.getMember().getLoginId();
            title = itemQA.getTitle();
            content = itemQA.getContent();
            isAnswer = itemQA.isAnswer();
            createdDate = itemQA.getCreatedDate();
            lastModifiedDate = itemQA.getLastModifiedDate();
        }

        public void setQAComment(QACommentDto.Response qaComment){

            this.qaComment = qaComment;
        }
    }


    // 상품 문의 목록 조회 (페이지)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ItemQAPageResponse{
        private List<ItemQAResponse> itemQAList;
        private int totalPage;
        private int nowPage;
    }


    // 내 상품 문의 목록 조회
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class MyItemQAResponse {
        private Long itemQAId;
        private ItemDto.SimpleItemResponse item;
        private String loginId;
        private String title;
        private String content;
        private boolean isAnswer;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;
        private QACommentDto.Response qaComment = null;


        public MyItemQAResponse(ItemQA itemQA, ItemDto.SimpleItemResponse simpleItemResponse, QACommentDto.Response qaComment){
            itemQAId = itemQA.getItemQAId();
            item = simpleItemResponse;
            loginId = itemQA.getMember().getLoginId();
            title = itemQA.getTitle();
            content = itemQA.getContent();
            isAnswer = itemQA.isAnswer();
            createdDate = itemQA.getCreatedDate();
            lastModifiedDate = itemQA.getLastModifiedDate();
            this.qaComment = qaComment;
        }
    }


    // 내 상품 문의 목록 조회 (페이지)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class MyItemQAPageResponse{
        private List<MyItemQAResponse> itemQAList;
        private int totalPage;
        private int nowPage;
    }
}
