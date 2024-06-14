package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.Member;
import project1.shop.domain.entity.Point;
import project1.shop.enumeration.PointStatus;

import java.time.LocalDateTime;
import java.util.List;

public class PointDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class PointHistoryResponse {
        private String pointStatus;
        private String pointHistory;
        private LocalDateTime time;


        public PointHistoryResponse(Point point){
            pointStatus = point.getPointStatus().getPointStatus();
            pointHistory = point.getPointHistory();
            time = point.getTime();
        }
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class PointHistoryListResponse {
        List<PointHistoryResponse> pointHistoryResponses;
        private int totalPoint;
        private int totalPage;
        private int nowPage;

    }
}
