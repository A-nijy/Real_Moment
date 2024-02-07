package project1.shop.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.adminService.AdminAnnouncementService;
import project1.shop.dto.innerDto.AnnouncementDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminAnnouncementController {

    private final AdminAnnouncementService adminAnnouncementService;



    // 공지사항 목록 조회
    @GetMapping("/admin/announcements")
    public List<AnnouncementDto.response> showAnnouncements() {

        List<AnnouncementDto.response> announcementsDto = adminAnnouncementService.showAnnouncements();

        return announcementsDto;
    }


    // 공지사항 상세 조회
    @GetMapping("/admin/announcement")
    public AnnouncementDto.response showAnnouncement(@RequestParam("announcementId") Long announcementId) {

        AnnouncementDto.response announcementDto = adminAnnouncementService.showAnnouncement(announcementId);

        return announcementDto;
    }


    // 공지사항 추가하기
    @PostMapping("/admin/announcement/{id}")
    public String saveAnnouncement(@PathVariable Long id, AnnouncementDto.SaveRequest request){

        adminAnnouncementService.saveAnnouncement(id, request);

        return "공지사항 추가 완료!";
    }


    // 공지사항 수정하기
    @PatchMapping("/admin/announcement/{id}")
    public String updateAnnouncement(@PathVariable Long id, AnnouncementDto.UpdateRequest request){

        adminAnnouncementService.updateAnnouncement(id, request);

        return "공지사항 수정 완료!";
    }


    // 공지사항 삭제하기
    @DeleteMapping("/admin/announcement/{id}")
    public String deleteAnnouncement(@PathVariable Long id, @RequestParam("announcementId") Long announcementId){

        adminAnnouncementService.deleteAnnouncement(id, announcementId);

        return "공지사항 삭제 완료!";
    }
}
