package rm.backend.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rm.backend.domain.entity.Grade;
import rm.backend.domain.repository.GradeRepository;
import rm.backend.dto.innerDto.GradeDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GradeService {

    private final GradeRepository gradeRepository;

    @Transactional
    public List<GradeDto.Response> showGrades() {

        List<Grade> grades = gradeRepository.findAll(Sort.by("gradePrice"));

        List<GradeDto.Response> gradesDto = grades.stream()
                .map(GradeDto.Response::new)
                .collect(Collectors.toList());

        return gradesDto;
    }
}
