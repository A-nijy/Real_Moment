package project1.shop.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    private Grade grade;
    private String id;
    private String password;
    private String email;
    private String name;
    private String nickname;
    private String tel;
    private Date birthDate;
    private String gender;
    private int point;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private LocalDateTime recentlyLogin;
    private boolean isMemberStatus;
    private boolean isLoginStatus;





}
