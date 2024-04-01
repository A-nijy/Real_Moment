package project1.shop.dto.innerDto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import project1.shop.domain.entity.Item;

import java.time.LocalDateTime;
import java.util.List;

public class ItemDto {


    // 상품 추가 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SaveRequest {
        private Long categoryId;
        private String name;
        private String content;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private int stock;
        private boolean isSell;
        private List<MultipartFile> mainImgList;
        private List<MultipartFile> subImgList;
    }



    // 상품 수정 버튼 클릭시 응답 데이터
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateResponse {
        private Long itemId;
        private Long categoryId;
        private String name;
        private String content;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private int stock;
        private boolean isSell;
        private boolean isDelete;
        private List<S3Dto.ImgDataResponse> mainImgDataList;
        private List<S3Dto.ImgDataResponse> subImgDataList;


        public UpdateResponse(Item item, List<S3Dto.ImgDataResponse> mainImgDataList, List<S3Dto.ImgDataResponse> subImgDataList){
            itemId = item.getItemId();
            categoryId = item.getItemId();
            name = item.getName();
            content = item.getContent();
            price = item.getPrice();
            discountRate = item.getDiscountRate();
            discountPrice = item.getDiscountPrice();
            sellPrice = item.getSellPrice();
            stock = item.getStock();
            isSell = item.isSell();
            isDelete = item.isDelete();

            this.mainImgDataList = mainImgDataList;
            this.subImgDataList = subImgDataList;
        }
    }


    // 상품 수정 요청
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateRequest {
        private Long itemId;
        private Long categoryId;
        private String name;
        private String content;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private int stock;
        private boolean isSell;

        private List<MultipartFile> mainImgList;
        private List<MultipartFile> subImgList;

        private List<S3Dto.ImgDataResponse> mainImgDataList;
        private List<S3Dto.ImgDataResponse> subImgDataList;
    }


    // 상품 수정 요청 (상품 정보)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateDataRequest {
        private Long itemId;
        private Long categoryId;
        private String name;
        private String content;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private int stock;
        private boolean isSell;
    }


    // 상품 메인 이미지 교체 요청 (상품 메인 이미지)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ReplaceImgRequest {

        private Long itemId;
        private MultipartFile imgFile;       // 추가한 이미지 파일
        private Long s3FileId;               // 삭제된 이미지 id
    }


    // 상품 메인 이미지 추가 요청 (상품 메인 이미지)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class AddImgRequest {

        private Long itemId;
        private MultipartFile imgFile;
        private int number;
    }


    // 상품 메인 이미지 순서 교체 요청 (상품 메인 이미지)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ChangeImgRequest {

        private Long itemId;
        private int number1;
        private int number2;
    }


    // 상품 메인 이미지 삭제 요청 (상품 메인 이미지)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class DeleteImgRequest {

        private Long itemId;
        private Long s3FileId;     // 삭제된 이미지 id
    }



    // 상품 간단 정보 (목록용)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SimpleItemResponse {
        private Long itemId;
        private String name;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private boolean isSell;
        private String mainImg;


        @QueryProjection
        public SimpleItemResponse (Item item){
            itemId = item.getItemId();
            name = item.getName();
            price = item.getPrice();
            discountRate = item.getDiscountRate();
            discountPrice = item.getDiscountPrice();
            sellPrice = item.getSellPrice();
            isSell = item.isSell();
//            mainImg = item.getMainImg();
        }
    }


    // 상품 간단 정보 (목록용)(관리자)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SimpleItemAdminResponse {
        private Long itemId;
        private String name;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private int sellCount;
        private int stock;
        private long revenue;
        private boolean isSell;
        private String mainImg;


        @QueryProjection
        public SimpleItemAdminResponse (Item item){
            itemId = item.getItemId();
            name = item.getName();
            price = item.getPrice();
            discountRate = item.getDiscountRate();
            discountPrice = item.getDiscountPrice();
            sellPrice = item.getSellPrice();
            sellCount = item.getSellCount();
            stock = item.getStock();
            revenue = item.getRevenue();
            isSell = item.isSell();
//            mainImg = item.getMainImg();
        }
    }


    // 상품 간단 정보 응답 (페이지)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SimpleItemPageResponse{
        private List<SimpleItemResponse> itemList;
        private int totalPage;
        private int nowPage;
    }

    // 상품 간단 정보 응답 (페이지) (관리자)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SimpleItemPageAdminResponse{
        private List<SimpleItemAdminResponse> itemList;
        private int totalPage;
        private int nowPage;
    }


    // 상품 상세 정보
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class FullItemResponse{
        private Long itemId;
        private String name;
        private String content;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;
        private int stock;
        private boolean isSell;
        private boolean isDelete;
        private String mainImg;
        private String subImg;


        public FullItemResponse(Item item){
            itemId = item.getItemId();
            name = item.getName();
            content = item.getContent();
            price = item.getPrice();
            discountRate = item.getDiscountRate();
            discountPrice = item.getDiscountPrice();
            sellPrice = item.getSellPrice();
            createdDate = item.getCreatedDate();
            lastModifiedDate = item.getLastModifiedDate();
            stock = item.getStock();
            isSell = item.isSell();
            isDelete = item.isDelete();
//            mainImg = item.getMainImg();
//            serveImg = item.getServeImg();
        }
    }


    // 상품 상세 정보 (관리자)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class FullItemAdminResponse{
        private Long itemId;
        private String name;
        private String content;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private int stock;
        private int sellCount;
        private long revenue;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;
        private boolean isSell;
        private boolean isDelete;
        private String mainImg;
        private String subImg;


        public FullItemAdminResponse(Item item){
            itemId = item.getItemId();
            name = item.getName();
            content = item.getContent();
            price = item.getPrice();
            discountRate = item.getDiscountRate();
            discountPrice = item.getDiscountPrice();
            sellPrice = item.getSellPrice();
            stock = item.getStock();
            sellCount = item.getSellCount();
            revenue = item.getRevenue();
            createdDate = item.getCreatedDate();
            lastModifiedDate = item.getLastModifiedDate();
            isSell = item.isSell();
            isDelete = item.isDelete();
//            mainImg = item.getMainImg();
//            serveImg = item.getServeImg();
        }
    }


//    // 상품 상세 페이지에 출력 (상품, 리뷰, 문의)
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Getter
//    @Setter
//    public static class ItemPageResponse{
//        private ItemDto.FullItemResponse item;
//        private List<ReviewDto.ReviewResponse> review;
//        private List<ItemQADto.ItemQAResponse> itemQA;
//    }


    // 주문 페이지에 응답할 상품 정보
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class OrderPageItemResponse {
        private Long itemId;
        private String name;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private boolean isSell;
        private String mainImg;
        private int count;
        private int totalSellPrice;


        public OrderPageItemResponse(Item item, int count){
            itemId = item.getItemId();
            name = item.getName();
            price = item.getPrice();
            discountRate = item.getDiscountRate();
            discountPrice = item.getDiscountPrice();
            sellPrice = item.getSellPrice();
            isSell = item.isSell();
//            mainImg = item.getMainImg();
            this.count = count;
            totalSellPrice = item.getSellPrice() * count;
        }
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
}
