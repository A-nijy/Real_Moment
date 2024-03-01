package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.ItemQA;

import java.time.LocalDateTime;

public class ItemQADto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ItemQARequest {
        private Long itemId;
        private String title;
        private String content;
    }


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

        private QACommentDto.Response qaComment;


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

        private QACommentDto.Response qaComment;


        public MyItemQAResponse(ItemQA itemQA){
            itemQAId = itemQA.getItemQAId();
            item = new ItemDto.SimpleItemResponse(itemQA.getItem());
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
}
