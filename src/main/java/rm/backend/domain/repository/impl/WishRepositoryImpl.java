package rm.backend.domain.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import rm.backend.domain.entity.QWish;
import rm.backend.domain.entity.Wish;
import rm.backend.domain.repository.custom.WishRepositoryCustom;

import java.util.List;

public class WishRepositoryImpl implements WishRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public WishRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }


    @Override
    public Page<Wish> searchPageWishList(Long memberId, Pageable pageable) {

        List<Wish> WishList = queryFactory.selectFrom(QWish.wish)
                .where(QWish.wish.member.memberId.eq(memberId),
                        QWish.wish.item.isDelete.eq(false))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Long total = queryFactory
                .select(QWish.wish.count())
                .from(QWish.wish)
                .where(QWish.wish.member.memberId.eq(memberId),
                        QWish.wish.item.isDelete.eq(false))
                .fetchOne();


        return new PageImpl<>(WishList, pageable, total);
    }
}
