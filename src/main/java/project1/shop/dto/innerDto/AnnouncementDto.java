package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.Announcement;

import java.time.LocalDateTime;

public class AnnouncementDto {


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
}
