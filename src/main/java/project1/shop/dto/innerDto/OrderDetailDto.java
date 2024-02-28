package project1.shop.dto.innerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project1.shop.domain.entity.OrderDetail;

public class OrderDetailDto {


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class response{

        private Long orderDetailId;
        private ItemDto.SimpleItemResponse simpleItem;
        private int Price;                      // 결제 당시 정가
        private int discountRate;               // 결제 당시 할인율
        private int discountPrice;              // 결제 당시 할인가
        private int sellPrice;                  // 결제 당시 판매가
        private int itemCount;                  // 결제 당시 구매 개수
        private int totalPrice;                 // 결제 당시 총 구매가


        public response(OrderDetail orderDetail){

            orderDetailId = orderDetail.getOrderDetailId();
            simpleItem = new ItemDto.SimpleItemResponse(orderDetail.getItem());
            Price = orderDetail.getPrice();
            discountRate = orderDetail.getDiscountRate();
            discountPrice = orderDetail.getDiscountPrice();
            sellPrice = orderDetail.getSellPrice();
            itemCount = orderDetail.getItemCount();
            totalPrice = orderDetail.getTotalPrice();
        }
    }
}
