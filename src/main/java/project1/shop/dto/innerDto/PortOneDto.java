package project1.shop.dto.innerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.*;

public class PortOneDto {


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class InicisResponse{

        private String merchantUid;             // 주문 번호
        private String itemName;                // 상품 이름
        private Long paymentPrice;              // 결제 금액
        private String buyerName;               // 구매자 이름
        private String buyerEmail;              // 구매자 이메일
        private String buyerAddress;            // 구매자 주소
    }



    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @ToString
    public static class InicisRequest {

        private String impUid;          // 결제 고유 번호 (imp_uid)
        private String merchantUid;     // 주문 고유 번호 (merchant_uid)



        // JSON 문자열을 받아와서 파싱하여 PaymentCallbackRequestDto 객체로 변환
        public static InicisRequest fromString(String request) {
            JsonObject requestJson = JsonParser.parseString(request).getAsJsonObject();
            String impUid = requestJson.getAsJsonPrimitive("imp_uid") == null ? null : requestJson.getAsJsonPrimitive("imp_uid").getAsString();
            String merchantUid = requestJson.getAsJsonPrimitive("merchant_uid") == null ? null : requestJson.getAsJsonPrimitive("merchant_uid").getAsString();

            return InicisRequest.builder()
                    .impUid(impUid)
                    .merchantUid(merchantUid)
                    .build();
        }
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CancelRequest{

        private String token;           // 에세스 토큰  (헤더로 담아서 따로 받을 예정 / 잠시동안만 body로 받음)
        private String merchantUid;     // 주문 고유 번호
        private String reason;          // 환불 or 취소 사유
    }
}
