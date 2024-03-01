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
    @GetMapping("/admin/announcementList")
    public List<AnnouncementDto.response> showAnnouncements(@RequestParam("nowPage") int nowPage) {

        List<AnnouncementDto.response> announcementsDto = adminAnnouncementService.showAnnouncements(nowPage);

        return announcementsDto;
    }


    // 공지사항 상세 조회
    @GetMapping("/admin/announcement")
    public AnnouncementDto.response showAnnouncement(@RequestParam("announcementId") Long announcementId) {

        AnnouncementDto.response announcementDto = adminAnnouncementService.showAnnouncement(announcementId);

        return announcementDto;
    }


    // 공지사항 추가하기
    @PostMapping("/admin/{id}/announcement")
    public String saveAnnouncement(@PathVariable Long id, AnnouncementDto.SaveRequest request){

        adminAnnouncementService.saveAnnouncement(id, request);

        return "공지사항 추가 완료!";
    }


    // 공지사항 수정버튼 클릭시 해당 공지사항 데이터 반환
    @GetMapping("/admin/{id}/announcement/data")
    public AnnouncementDto.UpdateResponse getAnnouncement(@PathVariable Long id, @RequestParam("announcementId") Long announcementId){

        AnnouncementDto.UpdateResponse announcementDto = adminAnnouncementService.getAnnouncement(id, announcementId);

        return announcementDto;
    }


    // 공지사항 수정하기
    @PatchMapping("/admin/{id}/announcement")
    public String updateAnnouncement(@PathVariable Long id, AnnouncementDto.UpdateRequest request){

        adminAnnouncementService.updateAnnouncement(id, request);

        return "공지사항 수정 완료!";
    }


    // 공지사항 삭제하기
    @DeleteMapping("/admin/announcement")
    public String deleteAnnouncement(@RequestParam("announcementId") Long announcementId){

        adminAnnouncementService.deleteAnnouncement(announcementId);

        return "공지사항 삭제 완료!";
    }
}
