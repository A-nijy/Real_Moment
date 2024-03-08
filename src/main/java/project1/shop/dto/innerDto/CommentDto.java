package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.Comment;

public class CommentDto {


    // 답변 작성 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Request {

        private Long oneOnOneId;
        private String content;
    }


    // 답변 조회 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response {

        private Long commentId;
        private String content;

        public Response(Comment comment){

            commentId = comment.getCommentId();
            content = comment.getContent();
        }
    }


    // 답변 수정 버튼 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateResponse{

        private Long commentId;
        private String content;


        public UpdateResponse(Comment comment){

            commentId = comment.getCommentId();
            content = comment.getContent();
        }
    }


    // 답변 수정 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateRequest{

        private Long commentId;
        private String content;
    }
}
