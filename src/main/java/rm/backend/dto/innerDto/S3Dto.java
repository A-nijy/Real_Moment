package rm.backend.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rm.backend.domain.entity.S3File;

public class S3Dto {



    // 파일 데이터 응답용
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ImgDataResponse {

        private Long s3FileId;
        private String fileName;
        private String fileUrl;

        public ImgDataResponse(S3File s3File){
            s3FileId = s3File.getS3FileId();
            fileName = s3File.getFileName();
            fileUrl = s3File.getFileUrl();
        }
    }


    // 삭제된 이미지 데이터 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class DeleteImgData {

        private Long s3FileId;
    }
}
