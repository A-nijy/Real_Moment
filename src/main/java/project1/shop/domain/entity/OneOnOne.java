package project1.shop.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.domain.AutoCheck.TimeCheck;
import project1.shop.dto.innerDto.OneOnOneDto;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OneOnOne extends TimeCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "one_on_one_id")
    private Long oneOnOneId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;
    private String content;
    private boolean isAnswer = false;


    public OneOnOne(Member member, OneOnOneDto.Request request){

        this.member = member;
        title = request.getTitle();
        content = request.getContent();
    }


    public void updateOneOnOne(OneOnOneDto.UpdateRequest request){

        title = request.getTitle();
        content = request.getContent();
    }


    public void saveIsAnswer(){
        isAnswer = true;
    }

    public void deleteIsAnswer(){
        isAnswer = false;
    }
}
