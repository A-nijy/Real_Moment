package project1.shop.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Admin;
import project1.shop.domain.entity.Announcement;
import project1.shop.domain.repository.AdminRepository;
import project1.shop.domain.repository.AnnouncementRepository;
import project1.shop.dto.innerDto.AnnouncementDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminAnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AdminRepository adminRepository;


    // 공지사항 목록 조회
    @Transactional
    public List<AnnouncementDto.response> showAnnouncements() {

        List<Announcement> announcements = announcementRepository.findAll();

        List<AnnouncementDto.response> announcementsDto = announcements.stream()
                .map(AnnouncementDto.response::new)
                .collect(Collectors.toList());

        return announcementsDto;
    }

    // 공지사항 조회
    @Transactional
    public AnnouncementDto.response showAnnouncement(Long announcementId) {

        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(IllegalArgumentException::new);

        announcement.viewCountUp();

        AnnouncementDto.response announcementDto = new AnnouncementDto.response(announcement);

        return announcementDto;
    }


    // 공지사항 추가
    @Transactional
    public void saveAnnouncement(Long id, AnnouncementDto.SaveRequest request) {

        Admin admin = adminRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        Announcement announcement = new Announcement(admin, request);

        announcementRepository.save(announcement);
    }

    // 공지사항 수정
    @Transactional
    public void updateAnnouncement(Long id, AnnouncementDto.UpdateRequest request) {

        Admin admin = adminRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        Announcement announcement = announcementRepository.findById(request.getAnnouncementId()).orElseThrow(IllegalArgumentException::new);

        announcement.UpdateAnnouncement(admin, request);
    }

    // 공지사항 삭제
    @Transactional
    public void deleteAnnouncement(Long id, Long announcementId) {

        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(IllegalArgumentException::new);

        announcementRepository.delete(announcement);
    }


}
