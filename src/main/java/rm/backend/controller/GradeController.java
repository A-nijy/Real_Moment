package rm.backend.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rm.backend.dto.innerDto.GradeDto;
import rm.backend.service.GradeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class GradeController {

    private final GradeService gradeService;


    // 등급 목록 조회
    @GetMapping("/gradeList")
    public List<GradeDto.Response> showGrades(){

        List<GradeDto.Response> gradesDto = gradeService.showGrades();

        return gradesDto;
    }
}
