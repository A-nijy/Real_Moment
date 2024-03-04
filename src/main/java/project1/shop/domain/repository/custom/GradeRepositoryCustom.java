package project1.shop.domain.repository.custom;

import project1.shop.domain.entity.Grade;

public interface GradeRepositoryCustom {

    Grade findHighestGrade(int totalPrice);

    Grade findOneLowGrade(Grade grade);

    Grade findOneHighGrade(Grade grade);
}
