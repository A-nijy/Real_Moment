package project1.shop.dto.innerDto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import project1.shop.domain.entity.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemberDto {



    // 회원 가입 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CreateRequest{

        @NotBlank(message = "아이디를 입력해주세요")
        @Size(min = 8, max = 20, message = "아이디는 8자 이상 20자 이하로 입력해주세요.")
        @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "아이디는 영문 대소문자, 숫자입력이 가능합니다.")
        private String loginId;

        @NotBlank(message = "비밀번호를 입력해주세요")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,20}$", message = "비밀번호는 영문 대소문자, 숫자, 특수기호(!, @, #, $, %, ^, &, *)가 포함되어야 합니다.")
        private String loginPassword;

        @NotBlank(message = "이메일을 입력해주세요")
        @Email(message = "올바른 이메일 주소를 입력해주세요.")
        private String email;

        @NotBlank(message = "성함을 입력해주세요")
        private String name;

        @NotBlank(message = "전화번호를 입력해주세요")
        @Pattern(regexp = "^(01[016789])-(\\d{3,4})-(\\d{4})$", message = "올바른 휴대폰 번호를 입력해주세요.")
        private String tel;

        @NotNull(message = "생년월일을 입력해주세요")
//        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "올바른 날짜 형식이 아닙니다. (YYYY-MM-DD)")
        @Past(message = "과거 날짜만 입력할 수 있습니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;

        @NotBlank(message = "성별을 입력해주세요")
        @Size(min = 1, max = 1, message = "한 글자가 아닙니다.")
        @Pattern(regexp = "^[남여]$", message = "남 또는 여를 입력해주세요")
        private String gender;

        private String roles;
    }


    // 아이디 중복 체크 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class IdCheckRequest {
        @NotBlank(message = "아이디를 입력해주세요")
        @Size(min = 8, max = 20, message = "아이디는 8자 이상 20자 이하로 입력해주세요.")
        @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "아이디는 영문 대소문자, 숫자입력이 가능합니다.")
        private String loginId;
    }


    // 로그인 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class LoginRequest{
        private String loginId;
        private String loginPassword;
    }


    // 비밀번호 수정 요청 (기본 비밀번호 + 새로운 비밀번호)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdatePasswordRequest{

        @NotBlank(message = "비밀번호를 입력해주세요")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,20}$", message = "비밀번호는 영문 대소문자, 숫자, 특수기호(!, @, #, $, %, ^, &, *)가 포함되어야 합니다.")
        private String loginPassword;

        @NotBlank(message = "비밀번호를 입력해주세요")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,20}$", message = "비밀번호는 영문 대소문자, 숫자, 특수기호(!, @, #, $, %, ^, &, *)가 포함되어야 합니다.")
        private String newLoginPassword;
    }


    // 개인 정보 수정 (이메일)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateEmailRequest{

        @NotBlank(message = "이메일을 입력해주세요")
        @Email(message = "올바른 이메일 주소를 입력해주세요.")
        private String email;
    }

    // 개인 정보 수정 (이름)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateNameRequest{

        @NotBlank(message = "성함을 입력해주세요")
        private String name;
    }

    // 개인 정보 수정 (생년월일)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateBirthRequest{

        @NotNull(message = "생년월일을 입력해주세요")
//        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "올바른 날짜 형식이 아닙니다. (YYYY-MM-DD)")
        @Past(message = "과거 날짜만 입력할 수 있습니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;
    }

    // 개인 정보 수정 (전화번호)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateTelRequest{

        @NotBlank(message = "전화번호를 입력해주세요")
        @Pattern(regexp = "^(01[016789])(\\d{3,4})(\\d{4})$", message = "올바른 휴대폰 번호를 입력해주세요.")
        private String tel;
    }


    // 회원 탈퇴를 위한 비밀번호 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class PasswordRequest{
        private String loginPassword;
    }




    // 회원 개인정보 확인용 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ProfileResponse {
        private Long memberId;
        private GradeDto.Response grade;
        private String loginId;
        private String email;
        private String name;
        private String tel;
        private String birthDate;
        private String gender;
        private int point;
        private LocalDateTime createdDate;


        public ProfileResponse(Member member, GradeDto.Response gradeDto){
            memberId = member.getMemberId();
            grade = gradeDto;
            loginId = member.getLoginId();
            email = member.getEmail();
            name = member.getName();
            tel = member.getTel();
            birthDate = member.getEmail();
            gender = member.getGender();
            point = member.getPoint();
            createdDate = member.getCreatedDate();

        }
    }



    // 회원 목록 검색용 간단 회원 정보 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SimpleProfileResponse{
        private Long memberId;
        private GradeDto.Response grade;
        private String loginId;
        private String name;
        private boolean isDelete;
        private LocalDateTime createdDate;


        public SimpleProfileResponse(Member member){
            memberId = member.getMemberId();
            grade = new GradeDto.Response(member.getGrade());
            loginId = member.getLoginId();
            name = member.getName();
            isDelete = member.isDelete();
            createdDate = member.getCreatedDate();

        }
    }


    // 회원의 개인정보 확인 응답 (관리자용)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class FullProfileResponse {
        private Long memberId;
        private GradeDto.Response grade;
        private String loginId;
        private String email;
        private String name;
        private String tel;
        private String birthDate;
        private String gender;
        private int point;
        private LocalDateTime recentlyLogin;
        private boolean isDelete;
        private LocalDateTime createdDate;


        public FullProfileResponse(Member member){
            memberId = member.getMemberId();
            grade = new GradeDto.Response(member.getGrade());
            loginId = member.getLoginId();
            email = member.getEmail();
            name = member.getName();
            tel = member.getTel();
            birthDate = member.getEmail();
            gender = member.getGender();
            point = member.getPoint();
            recentlyLogin = member.getRecentlyLogin();
            isDelete = member.isDelete();
            createdDate = member.getCreatedDate();

        }
    }

}
