package project1.shop.domain.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.OneOnOne;
import project1.shop.domain.entity.QItemQA;
import project1.shop.domain.entity.QMember;
import project1.shop.domain.entity.QOneOnOne;
import project1.shop.domain.repository.custom.OneOnOneRepositoryCustom;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;

@Slf4j
public class OneOnOneRepositoryImpl implements OneOnOneRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public OneOnOneRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }



    @Override
    public Page<OneOnOne> searchMyOneOnOne(SearchDto.OneOnOnes searchDto, Long memberId, Pageable pageable) {

        List<OneOnOne> oneOnOneList = queryFactory.selectFrom(QOneOnOne.oneOnOne)
                .join(QOneOnOne.oneOnOne.member, QMember.member)
                .where(answerEq(searchDto.getIsAnswer()),
                        QMember.member.memberId.eq(memberId))
                .orderBy(QOneOnOne.oneOnOne.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Long total = queryFactory
                .select(QOneOnOne.oneOnOne.count())
                .from(QOneOnOne.oneOnOne)
                .join(QOneOnOne.oneOnOne.member, QMember.member)
                .where(answerEq(searchDto.getIsAnswer()),
                        QMember.member.memberId.eq(memberId))
                .fetchOne();


        return new PageImpl<>(oneOnOneList, pageable, total);
    }


    @Override
    public Page<OneOnOne> searchOneOnOne(SearchDto.OneOnOneSearch searchDto, Pageable pageable) {

        List<OneOnOne> oneOnOneList = queryFactory.selectFrom(QOneOnOne.oneOnOne)
                .join(QOneOnOne.oneOnOne.member, QMember.member)
                .where(answerEq(searchDto.getIsAnswer()),
                        loginIdContains(searchDto.getLoginId()))
                .orderBy(QOneOnOne.oneOnOne.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(QOneOnOne.oneOnOne.count())
                .from(QOneOnOne.oneOnOne)
                .join(QOneOnOne.oneOnOne.member, QMember.member)
                .where(answerEq(searchDto.getIsAnswer()),
                        loginIdContains(searchDto.getLoginId()))
                .fetchOne();

        log.info("??");
        return new PageImpl<>(oneOnOneList, pageable, total);
    }


    //-------------------------------------------------------------------------

    private BooleanExpression answerEq(Boolean isAnswer){

        if(isAnswer == null){
            return null;
        }
        return QOneOnOne.oneOnOne.isAnswer.eq(isAnswer);
    }

    private BooleanExpression loginIdContains(String loginId){

        if (loginId == null){
            return null;
        }
        return loginId.isEmpty() ? null : QMember.member.loginId.contains(loginId);
    }
}
