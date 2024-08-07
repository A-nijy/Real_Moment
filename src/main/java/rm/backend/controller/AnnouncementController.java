package rm.backend.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rm.backend.dto.innerDto.AnnouncementDto;
import rm.backend.dto.innerDto.SearchDto;
import rm.backend.service.AnnounceService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class AnnouncementController {

    private final AnnounceService announceService;


    // 공지사항 목록 조회
    @GetMapping("/announcementList")
    public AnnouncementDto.SimplePageResponse showAnnouncements(SearchDto.Page request) {

        AnnouncementDto.SimplePageResponse announcementPageDto = announceService.showAnnouncements(request);

        return announcementPageDto;
    }


    // 공지사항 상세 조회
    @GetMapping("/announcement")
    public AnnouncementDto.FullResponse showAnnouncement(@RequestParam("announcementId") Long announcementId) {

        AnnouncementDto.FullResponse announcementDto = announceService.showAnnouncement(announcementId);

        return announcementDto;
    }
}
