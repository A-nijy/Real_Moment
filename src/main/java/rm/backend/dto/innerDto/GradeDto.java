package rm.backend.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rm.backend.domain.entity.Grade;

public class GradeDto {


    // 등급 추가 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CreateRequest{
        private String gradeName;
        private int gradePrice;
        private int rewardRate;
    }

    // 등급 수정 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateRequest{
        private Long gradeId;
        private String gradeName;
        private int rewardRate;
    }


    // 등급 모든 값 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response{
        private Long gradeId;
        private String gradeName;
        private int gradePrice;
        private int rewardRate;

        public Response(Grade grade){
            gradeId = grade.getGradeId();
            gradeName = grade.getGradeName();
            gradePrice = grade.getGradePrice();
            rewardRate = grade.getRewardRate();
        }
    }


}
