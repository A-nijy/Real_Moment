package project1.shop.dto.innerDto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
        private String mainImg;
        private String serveImg;
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
        private String mainImg;
        private String serveImg;

        public UpdateResponse(Item item){
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
            mainImg = item.getMainImg();
            serveImg = item.getServeImg();
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
        private String mainImg;
        private String serveImg;
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
            mainImg = item.getMainImg();
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
        private String serveImg;


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
            mainImg = item.getMainImg();
            serveImg = item.getServeImg();
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
            mainImg = item.getMainImg();
            this.count = count;
            totalSellPrice = item.getSellPrice() * count;
        }
    }
}
