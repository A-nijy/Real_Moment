package rm.backend.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rm.backend.domain.entity.Grade;
import rm.backend.domain.entity.Member;
import rm.backend.domain.repository.GradeRepository;
import rm.backend.domain.repository.MemberRepository;
import rm.backend.dto.innerDto.GradeDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminGradeService {

    private final GradeRepository gradeRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public List<GradeDto.Response> showGrades() {

        List<Grade> grades = gradeRepository.findAll(Sort.by("gradePrice"));

        List<GradeDto.Response> gradesDto = grades.stream()
                .map(GradeDto.Response::new)
                .collect(Collectors.toList());

        return gradesDto;
    }


    @Transactional
    public void saveGrade(GradeDto.CreateRequest request) {

        Grade gradeCheck = gradeRepository.findByGradePrice(request.getGradePrice()).orElse(null);

        if (gradeCheck != null){
            throw new IllegalArgumentException("이미 존재하는 등급 기준 금액입니다. 다른 등급 기준 금액을 사용해주세요");
        }

        // 추가할 등급 생성
        Grade grade = new Grade(request);

        // 해당 등급 보다 한 단계 높은 등급 가져오기
        Grade highGrade = gradeRepository.findOneHighGrade(grade);

        // 윗 등급이 존재한다면
        if (highGrade != null){

            // 추가할 등급에 해당하는 회원 목록 가져오기
            List<Member> memberList = memberRepository.findBetweenThisYearPay(grade, highGrade);

            // 해당하는 회원들 등급 업데이트 해주기
            for (Member member : memberList){
                member.updateGrade(grade);
            }
        } else {

            List<Member> memberList = memberRepository.findByThisYearPayGreaterThanEqual(grade.getGradePrice());

            for (Member member : memberList){
                member.updateGrade(grade);
            }
        }

        gradeRepository.save(grade);
    }


    @Transactional
    public void updateGrade(GradeDto.UpdateRequest request) {

        Grade grade = gradeRepository.findById(request.getGradeId()).orElseThrow(IllegalArgumentException::new);

        grade.update(request);
    }


    @Transactional
    public void deleteGrade(Long gradeId) {

        // 해당 등급 가져오기
        Grade grade = gradeRepository.findById(gradeId).orElseThrow(IllegalArgumentException::new);

        // 기본 등급인지 확인
        if (grade.getGradePrice() == 0) {
            throw new IllegalArgumentException("해당 등급은 수정만 가능합니다.");
        }

        // 해당 등급인 회원 리스트 가져오기
        List<Member> memberList = memberRepository.findByGrade_GradeId(gradeId);

        // 해당 등급 보다 바로 아래 등급 찾기
        Grade lowGrade = gradeRepository.findOneLowGrade(grade);

        // 회원들 등급 수정
        for (Member member : memberList) {

            member.updateGrade(lowGrade);
        }

        gradeRepository.delete(grade);
    }
}
