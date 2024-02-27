package project1.shop.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Grade;
import project1.shop.domain.repository.GradeRepository;
import project1.shop.dto.innerDto.GradeDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminGradeService {

    private final GradeRepository gradeRepository;


    @Transactional
    public List<GradeDto.Response> showGrades() {

        List<Grade> grades = gradeRepository.findAll();

        List<GradeDto.Response> gradesDto = grades.stream()
                .map(GradeDto.Response::new)
                .collect(Collectors.toList());

        return gradesDto;
    }


    @Transactional
    public void saveGrade(GradeDto.CreateRequest request) {

        Grade grade = new Grade(request);

        gradeRepository.save(grade);
    }


    @Transactional
    public void updateGrade(GradeDto.UpdateRequest request) {

        Grade grade = gradeRepository.findById(request.getGradeId()).orElseThrow(IllegalArgumentException::new);

        grade.update(request);
    }


    @Transactional
    public void deleteGrade(Long gradeId) {

        Grade grade = gradeRepository.findById(gradeId).orElseThrow(IllegalArgumentException::new);

        gradeRepository.delete(grade);
    }
}
