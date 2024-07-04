package rm.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rm.backend.domain.entity.Announcement;
import rm.backend.domain.repository.custom.AnnouncementRepositoryCustom;

public interface AnnouncementRepositoryRepository extends JpaRepository<Announcement, Long>, AnnouncementRepositoryCustom {
}
