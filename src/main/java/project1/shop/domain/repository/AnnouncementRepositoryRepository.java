package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Announcement;
import project1.shop.domain.repository.custom.AnnouncementRepositoryCustom;

public interface AnnouncementRepositoryRepository extends JpaRepository<Announcement, Long>, AnnouncementRepositoryCustom {
}
