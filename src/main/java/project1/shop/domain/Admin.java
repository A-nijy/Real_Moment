package project1.shop.domain;

import jakarta.persistence.*;

@Entity
public class Admin {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;
    private String id;
    private String password;
    private String email;
    private String name;
    private int grade;
}
