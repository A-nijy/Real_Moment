package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.Announcement;

import java.time.LocalDateTime;

public class AnnouncementDto {


    // 공지사항 목록 조회 응답 (+ 공지사항 상세 조회 응답)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class response {
        private Long announcementId;
        private String adminName;
        private String title;
        private String content;
        private boolean isFix;
        private int viewCount;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;


        public response(Announcement announcement){
            announcementId = announcement.getAnnouncementId();
            adminName = announcement.getAdmin().getName();
            title = announcement.getTitle();
            content = announcement.getContent();
            isFix = announcement.isFix();
            viewCount = announcement.getViewCount();
            createdDate = announcement.getCreatedDate();
            lastModifiedDate = announcement.getLastModifiedDate();

        }
    }


    // --------- ADMIN ---------------------------------------------------------------------------------------------------------------------


    // 공지사항 추가 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SaveRequest {
        private String title;
        private String content;
        private boolean isFix;
    }


    // 공지사항 수정 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateRequest {
        private Long AnnouncementId;
        private String title;
        private String content;
        private boolean isFix;
    }
}
