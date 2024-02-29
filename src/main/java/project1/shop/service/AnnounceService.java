package project1.shop.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Announcement;
import project1.shop.domain.repository.AnnouncementRepository;
import project1.shop.dto.innerDto.AnnouncementDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnnounceService {

    private final AnnouncementRepository announcementRepository;

    @Transactional
    public List<AnnouncementDto.response> showAnnouncements(int nowPage) {

        PageRequest pageRequest = PageRequest.of(nowPage - 1, 9);

        Page<Announcement> announcementList = announcementRepository.searchPage(pageRequest);

        List<AnnouncementDto.response> announcementsDto = announcementList.stream()
                .map(AnnouncementDto.response::new)
                .collect(Collectors.toList());

        return announcementsDto;
    }

    @Transactional
    public AnnouncementDto.response showAnnouncement(Long announcementId) {

        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(IllegalArgumentException::new);

        announcement.viewCountUp();

        AnnouncementDto.response announcementDto = new AnnouncementDto.response(announcement);

        return announcementDto;
    }
}
