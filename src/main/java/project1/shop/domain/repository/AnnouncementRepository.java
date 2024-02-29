package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Announcement;
import project1.shop.domain.repository.custom.AnnouncementCustom;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long>, AnnouncementCustom {
}
