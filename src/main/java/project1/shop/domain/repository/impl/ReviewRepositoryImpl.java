package project1.shop.domain.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.*;
import project1.shop.domain.repository.custom.ReviewRepositoryCustom;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;

public class ReviewRepositoryImpl implements ReviewRepositoryCustom {


    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }


    @Override
    public Page<Review> searchReviews(SearchDto.Reviews searchDto, Pageable pageable) {

        List<Review> reviews = queryFactory.selectFrom(QReview.review)
                                            .join(QReview.review.item, QItem.item)
                                            .where(itemIdEq(searchDto.getItemId()),
                                                    starEq(searchDto.getStar()),
                                                    QItem.item.isDelete.eq(false))
                                            .orderBy(QReview.review.createdDate.desc().nullsLast())
                                            .offset(pageable.getOffset())
                                            .limit(pageable.getPageSize())
                                            .fetch();


        Long total = queryFactory
                .select(QReview.review.count())
                .from(QReview.review)
                .join(QReview.review.item, QItem.item)
                .where(itemIdEq(searchDto.getItemId()),
                        starEq(searchDto.getStar()),
                        QItem.item.isDelete.eq(false))
                .fetchOne();


        return new PageImpl<>(reviews, pageable, total);
    }

    @Override
    public Page<Review> searchMyReviews(Long memberId, Pageable pageable) {

        List<Review> reviews = queryFactory.selectFrom(QReview.review)
                .join(QReview.review.member, QMember.member)
                .where(QMember.member.memberId.eq(memberId))
                .orderBy(QReview.review.createdDate.desc().nullsLast())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(QReview.review.count())
                .from(QReview.review)
                .join(QReview.review.member, QMember.member)
                .where(QMember.member.memberId.eq(memberId))
                .fetchOne();


        return new PageImpl<>(reviews, pageable, total);
    }


    //-------------------------------------------------------
    private BooleanExpression itemIdEq(Long itemId) {

        if(itemId == null){
            return null;
        }
        return QItem.item.itemId.eq(itemId);
    }

    private BooleanExpression starEq(Integer star) {

        if(star == null){
            return null;
        }
        return QReview.review.star.eq(star);
    }


}
