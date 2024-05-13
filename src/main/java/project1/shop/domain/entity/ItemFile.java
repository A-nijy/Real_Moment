package project1.shop.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s3_file_id")
    private S3File s3File;

    private String mainOrSub;

    private int number;


    public ItemFile(Item item, S3File s3File, String mainOrSub, int number){
        this.item = item;
        this.s3File = s3File;
        this.mainOrSub = mainOrSub;
        this.number = number;
    }


    // 순서 증가
    public void addNumber(){
        number++;
    }

    // 순서 감소
    public void subNumber(){
        number--;
    }

    // 순서 변경
    public void changeNumber(int number) {
        this.number = number;
    }
}
