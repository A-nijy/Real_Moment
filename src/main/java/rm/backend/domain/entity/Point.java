package rm.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rm.backend.enumeration.PointStatus;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long pointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private PointStatus pointStatus;

    private String pointHistory;

    private LocalDateTime time = LocalDateTime.now();


    public Point(Member member, PointStatus pointStatus, String pointHistory){

        this.member = member;
        this.pointStatus = pointStatus;
        this.pointHistory = pointHistory;
    }
}
