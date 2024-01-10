package project1.shop.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    private Level level;
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
