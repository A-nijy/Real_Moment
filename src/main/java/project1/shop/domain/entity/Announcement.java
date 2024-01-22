package project1.shop.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.domain.AutoCheck.TimeAndByCheck;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Announcement extends TimeAndByCheck {

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


    public void viewCountUp() {
        viewCount++;
    }
}
