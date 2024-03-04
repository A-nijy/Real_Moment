package project1.shop.domain.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.Grade;
import project1.shop.domain.entity.Member;
import project1.shop.domain.entity.QGrade;
import project1.shop.domain.entity.QMember;
import project1.shop.domain.repository.custom.MemberRepositoryCustom;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;

@Slf4j
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }



    @Override
    public Page<Member> searchMembers(SearchDto.Members searchDto, Pageable pageable) {

        log.info("test");
        List<Member> members;

         if (searchDto.getMemberSort() == null){

            members = queryFactory
                    .selectFrom(QMember.member)
                    .join(QMember.member.grade, QGrade.grade)
                    .where(loginIdContains(searchDto.getLoginId()),
                            gradeIdEq(searchDto.getGradeId()),
                            memberDeleteCheck(searchDto.getIsDelete()))
                    .orderBy(QMember.member.createdDate.desc().nullsLast())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

        } else if(searchDto.getMemberSort().equals("day")){

            members = queryFactory
                    .selectFrom(QMember.member)
                    .join(QMember.member.grade, QGrade.grade)
                    .where(loginIdContains(searchDto.getLoginId()),
                            gradeIdEq(searchDto.getGradeId()),
                            memberDeleteCheck(searchDto.getIsDelete()))
                    .orderBy(QMember.member.createdDate.desc().nullsLast())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

        } else if(searchDto.getMemberSort().equals("point")){

            members = queryFactory
                    .selectFrom(QMember.member)
                    .join(QMember.member.grade, QGrade.grade)
                    .where(loginIdContains(searchDto.getLoginId()),
                            gradeIdEq(searchDto.getGradeId()),
                            memberDeleteCheck(searchDto.getIsDelete()))
                    .orderBy(QMember.member.point.desc(), QMember.member.createdDate.desc().nullsLast())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        } else{

            members = queryFactory
                    .selectFrom(QMember.member)
                    .join(QMember.member.grade, QGrade.grade)
                    .where(loginIdContains(searchDto.getLoginId()),
                            gradeIdEq(searchDto.getGradeId()),
                            memberDeleteCheck(searchDto.getIsDelete()))
                    .orderBy(QMember.member.createdDate.desc().nullsLast())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }


        Long total = queryFactory
                .select(QMember.member.count())
                .from(QMember.member)
                .join(QMember.member.grade, QGrade.grade)
                .where(loginIdContains(searchDto.getLoginId()),
                        gradeIdEq(searchDto.getGradeId()),
                        memberDeleteCheck(searchDto.getIsDelete()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchOne();

        return new PageImpl<>(members, pageable, total);
    }


    @Override
    public List<Member> findThisYearPayMember() {


        List<Member> memberList = queryFactory.selectFrom(QMember.member)
                .where(QMember.member.isDelete.eq(false),
                        QMember.member.thisYearPay.gt(0))
                .fetch();


        return memberList;
    }


    // 두 등급 사이에 해당하는 회원 목록 조회
    @Override
    public List<Member> findBetweenThisYearPay(Grade grade, Grade highGrade) {

        List<Member> memberList = queryFactory.selectFrom(QMember.member)
                .where(QMember.member.thisYearPay.goe(grade.getGradePrice()),
                        QMember.member.thisYearPay.lt(highGrade.getGradePrice()))
                .fetch();


        return memberList;
    }


    //-------------------------------------------------

    private BooleanExpression loginIdContains(String loginId){

        if(loginId == null){
            return null;
        }
        return loginId.isEmpty() ? null : QMember.member.loginId.contains(loginId);
    }

    private BooleanExpression gradeIdEq(Long gradeId){

        return gradeId == null ? null : QGrade.grade.gradeId.eq(gradeId);
    }

    private BooleanExpression memberDeleteCheck(Boolean deleteCheck){

        if(deleteCheck == null){
            return null;
        }
        return QMember.member.isDelete.eq(deleteCheck);
    }
}
