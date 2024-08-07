package rm.backend.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rm.backend.domain.entity.OrderDetail;

public class OrderDetailDto {


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class response{

        private Long orderDetailId;
        private ItemDto.SimpleItemResponse item;
        private int Price;                      // 결제 당시 정가
        private int discountRate;               // 결제 당시 할인율
        private int discountPrice;              // 결제 당시 할인가
        private int sellPrice;                  // 결제 당시 판매가
        private int itemCount;                  // 결제 당시 구매 개수
        private int totalPrice;                 // 결제 당시 총 구매가

        private boolean isReviewStatus;         // 리뷰 작성 여부


        public response(OrderDetail orderDetail, ItemDto.SimpleItemResponse simpleItemResponse){

            orderDetailId = orderDetail.getOrderDetailId();
            item = simpleItemResponse;
            Price = orderDetail.getPrice();
            discountRate = orderDetail.getDiscountRate();
            discountPrice = orderDetail.getDiscountPrice();
            sellPrice = orderDetail.getSellPrice();
            itemCount = orderDetail.getItemCount();
            totalPrice = orderDetail.getTotalPrice();

            isReviewStatus = orderDetail.isReviewStatus();
        }
    }
}
