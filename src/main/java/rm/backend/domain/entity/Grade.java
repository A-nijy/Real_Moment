package rm.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rm.backend.dto.innerDto.GradeDto;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Long gradeId;
    private String gradeName = "White";
    private int gradePrice = 0;
    private int rewardRate = 1;



    public Grade(GradeDto.CreateRequest request){
        gradeName = request.getGradeName();
        gradePrice = request.getGradePrice();
        rewardRate = request.getRewardRate();
    }


    public void update(GradeDto.UpdateRequest request){
        gradeName = request.getGradeName();
        rewardRate = request.getRewardRate();
    }
}
