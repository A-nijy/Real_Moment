package rm.backend.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rm.backend.domain.entity.Announcement;

public interface AnnouncementRepositoryCustom {

    Page<Announcement> searchPage(Pageable pageable);
}
