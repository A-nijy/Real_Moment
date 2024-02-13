package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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


    // 상품 문의 댓글 수정 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateRequest{

        private Long qaCommentId;
        private String content;
    }


}
