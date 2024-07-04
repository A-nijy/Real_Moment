package rm.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rm.backend.domain.entity.Review;
import rm.backend.domain.repository.custom.ReviewRepositoryCustom;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

    List<Review> findByItem_ItemId(Long id);
    List<Review> findByMember_MemberId(Long id);
}
