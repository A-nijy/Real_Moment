package project1.shop.dto.innerDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.Admin;

import java.util.List;

public class AdminDto {



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


    // 관리자 가입 요청
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

        private String roles;
    }


    // 관리자 로그인 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class LoginRequest{
        private String loginId;
        private String loginPassword;
    }


    // 관리자 본인 정보 수정 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateRequest{
        private Long adminId;
        private String email;
        private String name;
    }



    // 관리자 권한 수정 요청 (고위 관리자)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class RoleUpdateRequest{
        private Long adminId;
        private String roles;
    }



    // 관리자 목록 조회 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response{
        private Long adminId;
        private String loginId;
        private String email;
        private String name;
        private String roles;


        public Response(Admin admin){
            adminId = admin.getAdminId();
            loginId = admin.getLoginId();
            email = admin.getEmail();
            name = admin.getName();
            roles = admin.getRoles();
        }
    }


    // 관리자 목록 조회 (페이지)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class PageResponse{
        private List<Response> adminList;
        private int totalPage;
        private int nowPage;
    }
}
