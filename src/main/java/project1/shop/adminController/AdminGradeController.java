package project1.shop.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.adminService.AdminGradeService;
import project1.shop.dto.innerDto.GradeDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminGradeController {

    private final AdminGradeService adminGradeService;


    // 등급 목록 조회
    @GetMapping("/admin/gradeList")
    public List<GradeDto.Response> showGrades(){

        List<GradeDto.Response> gradesDto = adminGradeService.showGrades();

        return gradesDto;
    }


    // 등급 추가
    @PostMapping("/admin/grade")
    public String saveGrade(@RequestBody GradeDto.CreateRequest request){

        adminGradeService.saveGrade(request);

        return "등급 추가 완료!";
    }


    // 등급 수정
    @PatchMapping("/admin/grade")
    public String updateGrade( @RequestBody GradeDto.UpdateRequest request){

        adminGradeService.updateGrade(request);

        return "등급 수정 완료!";
    }


    // 등급 삭제
    @DeleteMapping("/admin/grade")
    public String deleteGrade(@RequestParam("gradeId") Long gradeId){

        adminGradeService.deleteGrade(gradeId);

        return "등급 삭제 완료!";
    }
}
