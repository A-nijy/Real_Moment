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

    private String mainOrServe;


    public ItemFile(Item item, S3File s3File, String mainOrServe){
        this.item = item;
        this.s3File = s3File;
        this.mainOrServe = mainOrServe;
    }
}
