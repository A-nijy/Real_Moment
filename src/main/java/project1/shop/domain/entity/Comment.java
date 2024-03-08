package project1.shop.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.domain.AutoCheck.TimeAndByCheck;
import project1.shop.dto.innerDto.CommentDto;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Comment extends TimeAndByCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_on_one_id")
    private OneOnOne oneOnOne;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    private String content;


    public Comment(OneOnOne oneOnOne, Admin admin, CommentDto.Request request){

        this.oneOnOne = oneOnOne;
        this.admin = admin;
        content = request.getContent();
    }


    public void updateComment(Admin admin, CommentDto.UpdateRequest request){

        this.admin = admin;
        content = request.getContent();
    }
}
