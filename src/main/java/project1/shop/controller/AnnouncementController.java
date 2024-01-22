package project1.shop.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project1.shop.dto.innerDto.AnnouncementDto;
import project1.shop.service.AnnounceService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AnnouncementController {

    private final AnnounceService announceService;


    // 공지사항 목록 조회
    @GetMapping("/announcements")
    public List<AnnouncementDto.response> showAnnouncements() {

        List<AnnouncementDto.response> announcementsDto = announceService.showAnnouncements();

        return announcementsDto;
    }


    // 공지사항 상세 조회
    @GetMapping("/announcement")
    public AnnouncementDto.response showAnnouncement(@RequestParam("announcementId") Long announcementId) {

        AnnouncementDto.response announcementDto = announceService.showAnnouncement(announcementId);

        return announcementDto;
    }
}
