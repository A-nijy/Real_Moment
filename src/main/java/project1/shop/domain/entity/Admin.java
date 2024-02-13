package project1.shop.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.domain.AutoCheck.TimeCheck;
import project1.shop.dto.innerDto.AdminDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Admin extends TimeCheck {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;
    private String loginId;
    private String loginPassword;
    private String email;
    private String name;
    private String roles;
    private boolean isDeleteCheck = false;



    public void update(AdminDto.UpdateRequest request){
        email = request.getEmail();
        name = request.getName();
    }

    public void rolesUpdate(AdminDto.RoleUpdateRequest request){
        roles = request.getRoles();
    }

    public void delete(){
        loginId = null;
        loginPassword = null;
        email = null;
        name = null;
        roles = null;
        isDeleteCheck = true;
    }


    // enum으로 안하고 ,로 split하여 ROLE을 입력 -> 그걸 parsing
    public List<String> getRoleList(){
        if(this.roles.length()>0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
