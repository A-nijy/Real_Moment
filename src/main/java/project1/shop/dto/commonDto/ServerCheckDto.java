package project1.shop.dto.commonDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.Admin;
import project1.shop.domain.entity.Member;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServerCheckDto {

    private long memberAndAdminId;
    private String loginId;
    private String loginPassword;
    private String roles;


    public ServerCheckDto(Member member){
        memberAndAdminId = member.getMemberId();
        loginId = member.getLoginId();
        loginPassword = member.getLoginPassword();
        roles = member.getRoles();
    }

    public ServerCheckDto(Admin admin){
        memberAndAdminId = admin.getAdminId();
        loginId = admin.getLoginId();
        loginPassword = admin.getLoginPassword();
        roles = admin.getRoles();
    }

    public List<String> getRoleList(){
        if(this.roles.length()>0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
