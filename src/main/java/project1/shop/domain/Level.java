package project1.shop.domain;

import jakarta.persistence.*;

@Entity
public class Level {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
    private Long levelId;
    @OneToOne(mappedBy = "level")
    private Member member;
    private String level;
    private int rewardRate;


}
