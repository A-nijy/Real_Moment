package project1.shop.domain.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project1.shop.domain.entity.Grade;
import project1.shop.domain.entity.QGrade;
import project1.shop.domain.repository.custom.GradeRepositoryCustom;

public class GradeRepositoryImpl implements GradeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public GradeRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }


    @Override
    public Grade findHighestGrade(int totalPrice) {

        Grade grade = queryFactory.selectFrom(QGrade.grade)
                .where(QGrade.grade.gradePrice.loe(totalPrice))
                .orderBy(QGrade.grade.gradePrice.desc())
                .limit(1)
                .fetchOne();


        return grade;
    }
}
