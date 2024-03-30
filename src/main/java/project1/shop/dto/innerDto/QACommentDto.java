package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.QAComment;

public class QACommentDto {

    // 상품 문의 댓글 추가 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SaveRequest{

        private Long itemQAId;
        private String content;
    }


    // 상품 문의 댓글 수정 버튼 클릭시 해당 데이터를 가져와서 반환
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateResponse{

        private Long qaCommentId;
        private String content;

        public UpdateResponse(QAComment qaComment){

            qaCommentId = qaComment.getQACommentId();
            content = qaComment.getContent();
        }
    }


    // 상품 문의 댓글 수정 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateRequest{

        private Long qaCommentId;
        private String content;
    }


    // 상품 문의 댓글 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response {

        private Long qaCommentId;
        private Long itemQAId;
        private String content;

        public Response(QAComment qaComment){

            qaCommentId = qaComment.getQACommentId();
            itemQAId = qaComment.getQACommentId();
            content = qaComment.getContent();
        }
    }
}
