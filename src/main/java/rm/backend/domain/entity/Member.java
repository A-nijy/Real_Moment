package rm.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rm.backend.domain.AutoCheck.TimeCheck;
import rm.backend.dto.innerDto.MemberDto;
import rm.backend.enumeration.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Member extends TimeCheck {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    private Grade grade;
    private String loginId;
    private String loginPassword;
    private String email;
    private String name;
    private String tel;
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int thisYearPay = 0;
    private int point = 0;
    private LocalDateTime recentlyLogin = LocalDateTime.now();
    private boolean isDelete = false;
    private String roles;



    public Member(MemberDto.CreateRequest request, Grade grade){
        this.grade = grade;
        loginId = request.getLoginId();
        loginPassword = request.getLoginPassword();
        email = request.getEmail();
        name = request.getName();
        tel = request.getTel();
        birthDate = request.getBirthDate();
        gender = Gender.fromString(request.getGender());
        roles = request.getRoles();
    }

    // 최근 접속일 업데이트
    public void loginStatus(){
        recentlyLogin = LocalDateTime.now();
    }


    // 비밀번호 수정
    public void UpdatePassword(MemberDto.UpdatePasswordRequest request){
        loginPassword = request.getNewLoginPassword();
    }


    // 이메일 수정
    public void UpdateEmail(String email){
        this.email = email;
    }

    // 이름 수정
    public void UpdateName(String name){
        this.name = name;
    }

    // 생년월일 수정
    public void UpdateBirth(LocalDate birth){
        this.birthDate = birth;
    }

    // 전화번호 수정
    public void UpdateTel(String tel){
        this.tel = tel;
    }

    // 포인트 차감
    public void deletePoint(int point){
        this.point -= point;
    }


    // 구매 확정시 적립금, 올해 총 구매 금액 적용
    public void updateOrderMember(Order order){

        point += order.getGetPoint();
        thisYearPay += order.getBuyPrice();
    }

    // 구매 취소시 사용한 포인트 되돌리기
    public void usePointCancel(Order order){

        point += order.getUsePoint();
    }

    // 회원 등급 재정의
    public void updateGrade(Grade grade){
        this.grade = grade;
    }


    // 올해 총 구매금액 초기화
    public void resetThisYearPay(){
        thisYearPay = 0;
    }


    // 회원 탈퇴
    public void DeleteMember(){
        grade = null;
        loginId = null;
        loginPassword = null;
        email = null;
        name = null;
        tel = null;
        birthDate = null;
        gender = null;
        recentlyLogin = LocalDateTime.now();
        isDelete = true;
    }


    // enum으로 안하고 ,로 split하여 ROLE을 입력 -> 그걸 parsing
    public List<String> getRoleList(){
        if(this.roles.length()>0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
