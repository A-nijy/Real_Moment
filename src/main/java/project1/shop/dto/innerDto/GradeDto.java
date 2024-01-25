package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.Grade;

public class GradeDto {


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response{
        private Long gradeId;
        private String gradeName;
        private int rewardRate;

        public Response(Grade grade){
            gradeId = grade.getGradeId();
            gradeName = grade.getGradeName();
            rewardRate = grade.getRewardRate();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CreateRequest{
        private String gradeName;
        private int rewardRate;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateRequest{
        private Long gradeId;
        private String gradeName;
        private int rewardRate;
    }

}
