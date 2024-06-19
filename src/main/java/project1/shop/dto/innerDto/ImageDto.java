package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import project1.shop.domain.entity.PageFile;

import java.util.List;

public class ImageDto {


    // 이미지 단일 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ImageResponse {
        private Long pageFileId;
        private String imgUrl;

        private String linkUrl;
        private boolean isShow;

        public ImageResponse(PageFile pageFile){
            pageFileId = pageFile.getPageFileId();
            imgUrl = pageFile.getS3File().getFileUrl();

            linkUrl = pageFile.getLinkUrl();
            isShow = pageFile.isShow();
        }
    }

    // 이미지 리스트 응답
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ImageListResponse {
        private List<ImageResponse> imageListResponse;
    }

    // 이미지 요청 ("로고", "홍보", "배경")
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ImageRequest {
        private String imageLocation;
    }

//    // 이미지 저장 (초기)
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Getter
//    @Setter
//    public static class saveImageRequest {
//        private String imageLocation;
//        private List<MultipartFile> imgList;
//    }

    // 이미지 추가
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class AddImageRequest {
        private String imageLocation;
        private String linkUrl;
        private boolean show;
        private MultipartFile img;
    }

    // S3에 저장한 파일 데이터 응답용
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class S3Data{
        private String uuidFileName;
        private String s3Url;
    }

    // 이미지 순서 변경 데이터 요청 틀
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ChangeNumberImage{
        private Long pageFileId;
    }

    // 이미지 순서 변경 데이터 요청 리스트
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ChangeNumberImageList{
        private List<ChangeNumberImage> changeNumberImageList;
    }

    // 이미지 삭제 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class deleteImage{
        private Long pageFileId;
    }
}
