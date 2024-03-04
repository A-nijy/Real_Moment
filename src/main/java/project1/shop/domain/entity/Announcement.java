package project1.shop.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.domain.AutoCheck.TimeAndByCheck;
import project1.shop.dto.innerDto.AnnouncementDto;

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
    private boolean isFix = false;
    private int viewCount = 0;


    public Announcement(Admin admin, AnnouncementDto.SaveRequest request){

        this.admin = admin;
        title = request.getTitle();
        content = request.getContent();
        isFix = request.isFix();
    }

    public void UpdateAnnouncement(Admin admin, AnnouncementDto.UpdateRequest request){

        this.admin = admin;
        title = request.getTitle();
        content = request.getContent();
        isFix = request.isFix();
    }


    public void viewCountUp() {
        viewCount++;
    }
}
