package project1.shop.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.shop.enumeration.ImageLocation;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pageFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s3_file_id")
    private S3File s3File;

    @Enumerated(EnumType.STRING)
    private ImageLocation imageLocation;

    private String linkUrl;

    private boolean isShow;

    private int number;


    public PageFile(S3File s3File, String imageLocation, String linkUrl, boolean isShow, int number){
        this.s3File = s3File;
        this.imageLocation = ImageLocation.fromString(imageLocation);
        this.linkUrl = linkUrl;
        this.isShow = isShow;
        this.number = number;
    }

    // 순서 변경
    public void changeNumber(int number){
        this.number = number;
    }

    // 순서 감소
    public void subNumber(){
        number--;
    }
}
