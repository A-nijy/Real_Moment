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
        private boolean isSellCheck;
        private String mainImg;
        private String serveImg;
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
        private boolean isSellCheck;
        private boolean isDeleteCheck;
        private String mainImg;
        private String serveImg;
    }



    // 상품 간단 정보 (목록용)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SimpleItemResponse {
        private Long id;
        private String name;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private boolean isSellCheck;
        private String mainImg;


        @QueryProjection
        public SimpleItemResponse (Item item){
            id = item.getItemId();
            name = item.getName();
            price = item.getPrice();
            discountRate = item.getDiscountRate();
            discountPrice = item.getDiscountPrice();
            sellPrice = item.getSellPrice();
            isSellCheck = item.isSellCheck();
            mainImg = item.getMainImg();
        }
    }


    // 상품 상세 정보
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class FullItemResponse{
        private Long id;
        private String name;
        private String content;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;
        private int stock;
        private boolean isSellCheck;
        private boolean isDeleteCheck;
        private String mainImg;
        private String serveImg;


        public FullItemResponse(Item item){
            id = item.getItemId();
            name = item.getName();
            content = item.getContent();
            price = item.getPrice();
            discountRate = item.getDiscountRate();
            discountPrice = item.getDiscountPrice();
            sellPrice = item.getSellPrice();
            createdDate = item.getCreatedDate();
            lastModifiedDate = item.getLastModifiedDate();
            stock = item.getStock();
            isSellCheck = item.isSellCheck();
            isDeleteCheck = item.isDeleteCheck();
            mainImg = item.getMainImg();
            serveImg = item.getServeImg();
        }
    }


    // 상품 상세 페이지에 출력 (상품, 리뷰, 문의)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ItemPageResponse{
        private ItemDto.FullItemResponse itemResponseDto;
        private List<ReviewDto.ReviewResponse> reviewResponseDto;
        private List<ItemQADto.ItemQAResponse> itemQAResponseDto;
    }


    // 주문 페이지에 응답할 상품 정보
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class OrderPageItemResponse {
        private Long id;
        private String name;
        private int price;
        private int discountRate;
        private int discountPrice;
        private int sellPrice;
        private boolean isSellCheck;
        private String mainImg;
        private int count;
        private int totalSellPrice;


        public OrderPageItemResponse(Item item, int count){
            id = item.getItemId();
            name = item.getName();
            price = item.getPrice();
            discountRate = item.getDiscountRate();
            discountPrice = item.getDiscountPrice();
            sellPrice = item.getSellPrice();
            isSellCheck = item.isSellCheck();
            mainImg = item.getMainImg();
            this.count = count;
            totalSellPrice = item.getSellPrice() * count;
        }
    }
}
