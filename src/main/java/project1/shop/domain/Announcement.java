package project1.shop.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Announcement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private Long announcementId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;
    private String title;
    private String content;
    private boolean isFix;
    private int viewCount;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
