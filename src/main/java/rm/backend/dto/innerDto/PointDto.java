package rm.backend.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rm.backend.domain.entity.Point;

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
