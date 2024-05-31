package project1.shop.smtp;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class EmailCode {

    @Id
    private String email;                           // 이메일
    private String code;                            // 인증코드
    private LocalDateTime expiryDate;               // 인증 만료 시간


    // 인증코드 변경
    public void changeCode(String code, LocalDateTime expiryDate){
        this.code = code;
        this.expiryDate = expiryDate;
    }
}
