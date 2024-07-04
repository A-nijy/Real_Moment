package rm.backend.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rm.backend.domain.AutoCheck.TimeCheck;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class S3File extends TimeCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long s3FileId;

    // 크기 초과로 컬럼을 이용해서 크기 변경
    @Column(length = 1024)
    private String fileName;
    @Column(length = 1024)
    private String fileUrl;


    public S3File(String fileName, String fileUrl){
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}
