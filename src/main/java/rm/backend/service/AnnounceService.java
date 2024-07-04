package rm.backend.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rm.backend.domain.entity.Announcement;
import rm.backend.domain.repository.AnnouncementRepositoryRepository;
import rm.backend.dto.innerDto.AnnouncementDto;
import rm.backend.dto.innerDto.SearchDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnnounceService {

    private final AnnouncementRepositoryRepository announcementRepository;


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


    // 공지사항 상세 조회
    @Transactional
    public AnnouncementDto.FullResponse showAnnouncement(Long announcementId) {

        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(IllegalArgumentException::new);

        announcement.viewCountUp();

        AnnouncementDto.FullResponse announcementDto = new AnnouncementDto.FullResponse(announcement);

        return announcementDto;
    }
}
