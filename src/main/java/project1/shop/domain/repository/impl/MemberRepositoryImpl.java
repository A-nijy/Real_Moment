package project1.shop.domain.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.Member;
import project1.shop.domain.entity.QGrade;
import project1.shop.domain.entity.QMember;
import project1.shop.domain.repository.custom.MemberRepositoryCustom;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }



    @Override
    public Page<Member> searchMembers(SearchDto.Members searchDto, Pageable pageable) {

        List<Member> members;

        if(searchDto.getMemberSort().equals("day")){

            members = queryFactory
                    .selectFrom(QMember.member)
                    .join(QMember.member.grade, QGrade.grade)
                    .where(loginIdContains(searchDto.getLoginId()),
                            gradeIdEq(searchDto.getGradeId()),
                            memberDeleteCheck(searchDto.getIsDelete()))
                    .orderBy(QMember.member.createdDate.desc(), QMember.member.createdDate.desc().nullsLast())
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
        } else {

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
                .orderBy(QMember.member.createdDate.desc().nullsLast())
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
