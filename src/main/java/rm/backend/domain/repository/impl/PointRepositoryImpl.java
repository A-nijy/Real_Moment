package rm.backend.domain.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import rm.backend.domain.entity.Point;
import rm.backend.domain.entity.QPoint;
import rm.backend.domain.repository.custom.PointRepositoryCustom;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class PointRepositoryImpl implements PointRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PointRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }


    @Override
    public Page<Point> searchPointHistory(Long memberId, Pageable pageable) {

        List<Point> points = queryFactory.selectFrom(QPoint.point)
                .where(QPoint.point.member.memberId.eq(memberId),
                        QPoint.point.time.goe(LocalDateTime.now().minusMonths(3)))
                .orderBy(QPoint.point.time.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Long total = queryFactory
                .select(QPoint.point.count())
                .from(QPoint.point)
                .where(QPoint.point.member.memberId.eq(memberId),
                        QPoint.point.time.goe(LocalDateTime.now().minusMonths(3)))
                .fetchOne();


        return new PageImpl<>(points, pageable, total);
    }
}
