package project1.shop.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.Point;

public interface PointRepositoryCustom {

    Page<Point> searchPointHistory(Long memberId, Pageable pageable);
}
