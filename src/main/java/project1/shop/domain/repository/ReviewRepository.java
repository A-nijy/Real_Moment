package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Review;
import project1.shop.domain.repository.custom.ReviewRepositoryCustom;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

    List<Review> findByItem_ItemId(Long id);
    List<Review> findByMember_MemberId(Long id);
}
