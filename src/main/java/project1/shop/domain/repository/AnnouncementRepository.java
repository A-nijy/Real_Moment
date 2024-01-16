package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
