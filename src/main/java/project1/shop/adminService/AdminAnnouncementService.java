package project1.shop.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Admin;
import project1.shop.domain.entity.Announcement;
import project1.shop.domain.repository.AdminRepository;
import project1.shop.domain.repository.AnnouncementRepositoryRepository;
import project1.shop.dto.innerDto.AnnouncementDto;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminAnnouncementService {

    private final AnnouncementRepositoryRepository announcementRepository;
    private final AdminRepository adminRepository;


    // 공지사항 목록 조회
    @Transactional
    public AnnouncementDto.SimplePageResponse showAnnouncements(SearchDto.Page request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 9);

        Page<Announcement> announcementList = announcementRepository.searchPage(pageRequest);

        List<AnnouncementDto.SimpleResponse> announcementsDto = announcementList.stream()
                .map(AnnouncementDto.SimpleResponse::new)
                .collect(Collectors.toList());

        AnnouncementDto.SimplePageResponse announcementPageDto = new AnnouncementDto.SimplePageResponse(announcementsDto, announcementList.getTotalPages(), request.getNowPage());

        return announcementPageDto;
    }

    // 공지사항 조회
    @Transactional
    public AnnouncementDto.FullResponse showAnnouncement(Long announcementId) {

        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(IllegalArgumentException::new);

        announcement.viewCountUp();

        AnnouncementDto.FullResponse announcementDto = new AnnouncementDto.FullResponse(announcement);

        return announcementDto;
    }


    // 공지사항 추가
    @Transactional
    public void saveAnnouncement(Long id, AnnouncementDto.SaveRequest request) {

        Admin admin = adminRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        Announcement announcement = new Announcement(admin, request);

        announcementRepository.save(announcement);
    }


    // 공지사항 수정버튼 클릭시 해당 데이터 가져와서 반환
    @Transactional
    public AnnouncementDto.UpdateResponse getAnnouncement(Long id, Long announcementId) {

        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(IllegalArgumentException::new);

        AnnouncementDto.UpdateResponse announcementDto = new AnnouncementDto.UpdateResponse(announcement);

        return announcementDto;
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
    public void deleteAnnouncement(Long announcementId) {

        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(IllegalArgumentException::new);

        announcementRepository.delete(announcement);
    }



}
