package rm.backend.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rm.backend.domain.entity.Comment;
import rm.backend.domain.entity.OneOnOne;

import java.util.List;

public class OneOnOneDto {


    // 1대1 문의 작성 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Request{

        private String title;
        private String content;
    }


    // 내가 작성한 1대1 문의 목록 조회 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class MyResponse {

        private Long oneOnOneId;
        private String title;
        private String content;
        private boolean isAnswer;

        private CommentDto.Response comment = null;

        public MyResponse(OneOnOne oneOnOne){

            oneOnOneId = oneOnOne.getOneOnOneId();
            title = oneOnOne.getTitle();
            content = oneOnOne.getContent();
            isAnswer = oneOnOne.isAnswer();
        }

        public void setComment(Comment comment){
            this.comment = new CommentDto.Response(comment);
        }
    }


    // 내 상품 문의 목록 조회 (페이지)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class MyPageResponse{
        private List<MyResponse> oneOnOneList;
        private int totalPage;
        private int nowPage;
    }


    // 내가 작성한 1대1 문의 목록 조회 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response {

        private Long oneOnOneId;
        private String loginId;
        private String title;
        private String content;
        private boolean isAnswer;

        private CommentDto.Response comment = null;

        public Response(OneOnOne oneOnOne){

            oneOnOneId = oneOnOne.getOneOnOneId();
            loginId = oneOnOne.getMember().getLoginId();
            title = oneOnOne.getTitle();
            content = oneOnOne.getContent();
            isAnswer = oneOnOne.isAnswer();
        }

        public void setComment(Comment comment){
            this.comment = new CommentDto.Response(comment);
        }
    }


    // 내 상품 문의 목록 조회 (페이지)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class PageResponse{
        private List<Response> oneOnOneList;
        private int totalPage;
        private int nowPage;
    }


    // 1대1 문의 수정 버튼 응답 (내용 받아오기)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateResponse{

        private Long oneOnOneId;
        private String title;
        private String content;


        public UpdateResponse(OneOnOne oneOnOne){
            oneOnOneId = oneOnOne.getOneOnOneId();
            title = oneOnOne.getTitle();
            content = oneOnOne.getContent();
        }
    }


    // 1대1 문의 수정 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateRequest{

        private Long oneOnOneId;
        private String title;
        private String content;
    }
}
