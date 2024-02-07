package project1.shop.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.domain.AutoCheck.TimeCheck;
import project1.shop.dto.innerDto.MemberDto;

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
//    private String nickname;
    private String tel;
    private LocalDate birthDate;
    private String gender;
    private int point = 0;
    private LocalDateTime recentlyLogin = LocalDateTime.now();
//    private boolean isMemberStatus = true;
//    private boolean isLoginStatus = false;
    private boolean isDelete = false;
    private String roles;



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
        roles = request.getRoles();
    }

    public void loginStatus(){
//        isLoginStatus = true;
        recentlyLogin = LocalDateTime.now();
    }

//    public void logoutStatus(){
//        isLoginStatus = false;
//    }

    public void UpdatePassword(MemberDto.UpdatePasswordRequest request){
        loginPassword = request.getNewLoginPassword();
    }

    public void UpdateProfile(MemberDto.UpdateProfileRequest request){
        email = request.getEmail();
        name = request.getName();
        tel = request.getTel();
        gender = request.getGender();
    }


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
