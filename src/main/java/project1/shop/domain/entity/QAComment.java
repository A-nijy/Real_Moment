package project1.shop.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.domain.AutoCheck.TimeAndByCheck;
import project1.shop.dto.innerDto.QACommentDto;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QAComment extends TimeAndByCheck {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QA_comment_id")
    private Long QACommentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_QA_id")
    private ItemQA itemQA;
    private String content;


    public QAComment(Admin admin, ItemQA itemQA, QACommentDto.SaveRequest request){

        this.admin = admin;
        this.itemQA = itemQA;
        content = request.getContent();
    }


    public void update(Admin admin, QACommentDto.UpdateRequest request){

        this.admin = admin;
        content = request.getContent();
    }
}
