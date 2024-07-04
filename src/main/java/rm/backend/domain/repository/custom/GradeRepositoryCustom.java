package rm.backend.domain.repository.custom;

import rm.backend.domain.entity.Grade;

public interface GradeRepositoryCustom {

    Grade findHighestGrade(int totalPrice);

    Grade findOneLowGrade(Grade grade);

    Grade findOneHighGrade(Grade grade);
}
