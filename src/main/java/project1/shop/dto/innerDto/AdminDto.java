package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.Admin;

public class AdminDto {


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
}
