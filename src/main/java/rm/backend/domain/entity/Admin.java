package rm.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rm.backend.domain.AutoCheck.TimeCheck;
import rm.backend.dto.innerDto.AdminDto;

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
    private String email = null;
    private String name = null;
    private String roles;
    private boolean isDelete = false;


    public Admin(AdminDto.CreateRequest request){
        loginId = request.getLoginId();
        loginPassword = request.getLoginPassword();
        roles = request.getRoles();
    }

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
