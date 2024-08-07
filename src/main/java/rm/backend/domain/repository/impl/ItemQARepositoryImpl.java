package rm.backend.domain.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import rm.backend.domain.entity.*;
import rm.backend.domain.repository.custom.ItemQARepositoryCustom;
import rm.backend.dto.innerDto.SearchDto;

import java.util.List;

public class ItemQARepositoryImpl implements ItemQARepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ItemQARepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }


    @Override
    public Page<ItemQA> searchAdminItemQAs(SearchDto.AdminItemQA searchDto, Pageable pageable) {

        List<ItemQA> itemQAs = queryFactory.selectFrom(QItemQA.itemQA)
                .join(QItemQA.itemQA.item, QItem.item)
                .where(itemIdEq(searchDto.getItemId()),
                        answerEq(searchDto.getIsAnswer()),
                        QItem.item.isDelete.eq(false))
                .orderBy(QItemQA.itemQA.createdDate.desc().nullsLast())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Long total = queryFactory
                .select(QItemQA.itemQA.count())
                .from(QItemQA.itemQA)
                .join(QItemQA.itemQA.item, QItem.item)
                .where(itemIdEq(searchDto.getItemId()),
                        answerEq(searchDto.getIsAnswer()),
                        QItem.item.isDelete.eq(false))
                .fetchOne();


        return new PageImpl<>(itemQAs, pageable, total);
    }


    @Override
    public Page<ItemQA> searchItemInItemQAs(SearchDto.ItemInItemQA searchDto, Pageable pageable) {

        List<ItemQA> itemQAs = queryFactory.selectFrom(QItemQA.itemQA)
                .join(QItemQA.itemQA.item, QItem.item)
                .where(itemIdEq(searchDto.getItemId()),
                        QItem.item.isDelete.eq(false))
                .orderBy(QItemQA.itemQA.createdDate.desc().nullsLast())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Long total = queryFactory
                .select(QItemQA.itemQA.count())
                .from(QItemQA.itemQA)
                .join(QItemQA.itemQA.item, QItem.item)
                .where(itemIdEq(searchDto.getItemId()),
                        QItem.item.isDelete.eq(false))
                .fetchOne();


        return new PageImpl<>(itemQAs, pageable, total);
    }


    @Override
    public Page<ItemQA> searchMyItemQAs(Long memberId, Pageable pageable) {

        List<ItemQA> itemQAs = queryFactory.selectFrom(QItemQA.itemQA)
                .join(QItemQA.itemQA.member, QMember.member)
                .join(QItemQA.itemQA.item, QItem.item)
                .where(QMember.member.memberId.eq(memberId),
                        QItem.item.isDelete.eq(false))
                .orderBy(QItemQA.itemQA.createdDate.desc().nullsLast())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Long total = queryFactory
                .select(QItemQA.itemQA.count())
                .from(QItemQA.itemQA)
                .join(QItemQA.itemQA.member, QMember.member)
                .join(QItemQA.itemQA.item, QItem.item)
                .where(QMember.member.memberId.eq(memberId),
                        QItem.item.isDelete.eq(false))
                .fetchOne();


        return new PageImpl<>(itemQAs, pageable, total);
    }


    //-----------------------------------------------------
    private BooleanExpression itemIdEq(Long itemId){

        if(itemId == null){
            return null;
        }
        return QItem.item.itemId.eq(itemId);
    }

    private BooleanExpression answerEq(Boolean isAnswer){

        if(isAnswer == null){
            return null;
        }
        return QItemQA.itemQA.isAnswer.eq(isAnswer);
    }
}
