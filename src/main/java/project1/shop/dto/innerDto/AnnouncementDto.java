package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.Announcement;

import java.time.LocalDateTime;
import java.util.List;

public class AnnouncementDto {


    // 공지사항 목록 조회 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SimpleResponse {
        private Long announcementId;
        private String adminName;
        private String title;
        private String content;
        private boolean isFix;
        private int viewCount;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;


        public SimpleResponse(Announcement announcement){
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


    // 공지사항 목록 조회 (페이지)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SimplePageResponse{
        private List<SimpleResponse> announementList;
        private int totalPage;
        private int nowPage;
    }


    // 공지사항 상세 조회 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class FullResponse {
        private Long announcementId;
        private String adminName;
        private String title;
        private String content;
        private boolean isFix;
        private int viewCount;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;


        public FullResponse(Announcement announcement){
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
        private boolean isFix = false;
    }


    // 공지사항 수정버튼 클릭시 해당 공지사항 데이터 반환용
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateResponse {
        private Long announcementId;
        private String title;
        private String content;
        private boolean isFix;

        public UpdateResponse(Announcement announcement){
            announcementId = announcement.getAnnouncementId();
            title = announcement.getTitle();
            content = announcement.getContent();
            isFix = announcement.isFix();
        }
    }


    // 공지사항 수정 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateRequest {
        private Long announcementId;
        private String title;
        private String content;
        private boolean isFix;
    }
}
