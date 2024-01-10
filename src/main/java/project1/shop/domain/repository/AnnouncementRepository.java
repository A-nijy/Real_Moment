package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.Announcement;
import project1.shop.domain.Level;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
