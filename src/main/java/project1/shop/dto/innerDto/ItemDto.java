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
        private int sellPrice;
        private boolean isSellCheck;
        private String mainImg;


        @QueryProjection
        public SimpleItemResponse (Item item){
            id = item.getItemId();
            name = item.getName();
            price = item.getPrice();
            discountRate = item.getDiscountRate();
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
}
