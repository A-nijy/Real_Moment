package project1.shop.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class QAComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QA_comment_id")
    private Long QACommentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemQA_id")
    private ItemQA itemQA;
    private String content;
    private LocalDateTime writtenDate;
    private LocalDateTime editedDate;
}
