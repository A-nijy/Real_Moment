package project1.shop.adminService;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Item;
import project1.shop.domain.entity.OrderDetail;
import project1.shop.domain.entity.Order;
import project1.shop.domain.repository.ItemRepository;
import project1.shop.domain.repository.OrderDetailRepository;
import project1.shop.domain.repository.OrderRepository;
import project1.shop.dto.innerDto.OrderDetailDto;
import project1.shop.dto.innerDto.OrderDto;
import project1.shop.dto.innerDto.SearchDto;
import project1.shop.enumeration.PaymentStatus;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminOrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ItemRepository itemRepository;

    @Value("${imp.api.key}")
    private String apiKey;
    @Value("${imp.api.secret_key}")
    private String secretKey;



    // 주문 목록 조회
    @Transactional
    public OrderDto.OrderPageResponse showOrders(SearchDto.OrdersSearch request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 5);


        Page<Order> orders = orderRepository.searchOrders(request, pageRequest);

        List<OrderDto.OrderResponse> ordersDto = orders.stream()
                .map(OrderDto.OrderResponse::new)
                .collect(Collectors.toList());


        for (OrderDto.OrderResponse orderDto : ordersDto){

            List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_OrderId(orderDto.getOrderId());

            List<OrderDetailDto.response> orderDetailsDto = orderDetails.stream()
                    .map(OrderDetailDto.response::new)
                    .collect(Collectors.toList());

            orderDto.plusOrderDetails(orderDetailsDto);
        }

        OrderDto.OrderPageResponse orderPageDto = new OrderDto.OrderPageResponse(ordersDto, orders.getTotalPages(), request.getNowPage());

        return orderPageDto;
    }


    // 주문 상세 조회
    @Transactional
    public OrderDto.AdminOrderDetailResponse showOrder(Long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);

        OrderDto.AdminOrderResponse orderResponse = new OrderDto.AdminOrderResponse(order);

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);

        List<OrderDetailDto.response> orderDetailsDto = orderDetails.stream()
                .map(OrderDetailDto.response::new)
                .collect(Collectors.toList());

        orderResponse.plusOrderDetails(orderDetailsDto);



        OrderDto.AdminOrderDetailResponse orderDetailResponse = new OrderDto.AdminOrderDetailResponse(orderResponse, order);

        return orderDetailResponse;
    }


    // 주문 상태 변경하기 [ 결제준비, 결제완료, 배송준비, 배송중, 배송완료, 결제취소, 환불요청, 환불완료 ]
    @Transactional
    public void updateOrderStatus(OrderDto.AdminOrderStatus request) {

        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(IllegalArgumentException::new);

        PaymentStatus status = PaymentStatus.fromString(request.getStatus());

        order.updateStatus(status);
    }



    // 결제 취소 토큰 발급 후 바로 결제 취소하기
    @Transactional
    public void orderCancel(OrderDto.CancelRequest request) throws IOException {

        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(IllegalArgumentException::new);


        // 1. 토큰 발급받기

        URL getTokenUrl = new URL("https://api.iamport.kr/users/getToken");
        HttpsURLConnection getTokenConn = (HttpsURLConnection) getTokenUrl.openConnection();

        // 요청 방식을 POST 메서드로 설정
        getTokenConn.setRequestMethod("POST");

        // 요청의 Content-Type과 Accept 헤더 설정
        getTokenConn.setRequestProperty("Content-Type", "application/json");
        getTokenConn.setRequestProperty("Accept", "application/json");

        // 해당 연결을 출력 스트림(요청)으로 사용
        getTokenConn.setDoOutput(true);

        // JSON 객체에 해당 API가 필요로하는 데이터 추가
        JsonObject getTokenJson = new JsonObject();
        getTokenJson.addProperty("imp_key", apiKey);
        getTokenJson.addProperty("imp_secret", secretKey);

        // 출력 스트림으로 해당 conn에 요청
        BufferedWriter getTokenBw = new BufferedWriter(new OutputStreamWriter(getTokenConn.getOutputStream()));
        getTokenBw.write(getTokenJson.toString()); // getTokenJson 객체를 문자열 형태로 HTTP 요청 본문에 추가
        getTokenBw.flush();                 // BufferedWriter 비우기
        getTokenBw.close();                 // BufferedWriter 종료

        // 입력 스트립으로 getTokenConn 요청에 대한 응답 반환
        BufferedReader getTokenBr = new BufferedReader(new InputStreamReader(getTokenConn.getInputStream()));
        Gson getTokenGson = new Gson();     // 응답 데이터를 자바 객체로 변환
        String response = getTokenGson.fromJson(getTokenBr.readLine(), Map.class).get("response").toString();
        String cancelToken = getTokenGson.fromJson(response, Map.class).get("access_token").toString();
        getTokenBr.close(); // BufferedReader 종료

        getTokenConn.disconnect(); // 연결 종료

        log.info("결제 취소용 토큰 발급 완료");


        // 2. 발급 받은 토큰을 이용하여 결제 취소하기

        URL url = new URL("https://api.iamport.kr/payments/cancel");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        // 요청 방식을 POST로 설정
        conn.setRequestMethod("POST");

        // 요청의 Content-Type, Accept, Authorization 헤더 설정
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", cancelToken);

        // 해당 연결을 출력 스트림(요청)으로 사용
        conn.setDoOutput(true);

        // JSON 객체에 해당 API가 필요로하는 데이터 추가.
        JsonObject json = new JsonObject();
        json.addProperty("merchant_uid", order.getMerchantUid());
        json.addProperty("reason", request.getReasonText());

        // 출력 스트림으로 해당 conn에 요청
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(json.toString());
        bw.flush();
        bw.close();

        // 입력 스트림으로 conn 요청에 대한 응답 반환
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        br.close();
        conn.disconnect();

        log.info("결제가 취소되었습니다.");

        // 상품 재고 다시 되돌리기
        plusStock(order);
    }



    // 주문 취소로 인해 상품 재고 다시 채우기
    public void plusStock(Order order){

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);

        for (OrderDetail orderDetail : orderDetails){

            Item item = itemRepository.findById(orderDetail.getItem().getItemId()).orElseThrow(IllegalArgumentException::new);

            item.plusStock(orderDetail.getItemCount());
        }
    }
}
