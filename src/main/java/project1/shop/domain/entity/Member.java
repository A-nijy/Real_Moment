package project1.shop.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.domain.AutoCheck.TimeCheck;
import project1.shop.dto.innerDto.MemberDto;

import java.time.LocalDate;
import java.time.LocalDateTime;


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
//    private String nickname;
    private String tel;
    private LocalDate birthDate;
    private String gender;
    private int point = 0;
    private LocalDateTime recentlyLogin = LocalDateTime.now();
//    private boolean isMemberStatus = true;
//    private boolean isLoginStatus = false;



    public Member(MemberDto.CreateRequest request, Grade grade){
        this.grade = grade;
        loginId = request.getLoginId();
        loginPassword = request.getLoginPassword();
        email = request.getEmail();
        name = request.getName();
//        nickname = request.getName();
        tel = request.getTel();
        birthDate = request.getBirthDate();
        gender = request.getGender();
    }

    public void loginStatus(){
//        isLoginStatus = true;
        recentlyLogin = LocalDateTime.now();
    }

//    public void logoutStatus(){
//        isLoginStatus = false;
//    }

    public void UpdatePassword(MemberDto.UpdatePasswordRequest request){
        loginPassword = request.getLoginPassword();
    }

    public void UpdateProfile(MemberDto.UpdateProfileRequest request){
        email = request.getEmail();
        name = request.getName();
        tel = request.getTel();
        gender = request.getGender();
    }
}
