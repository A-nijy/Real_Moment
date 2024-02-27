package project1.shop.service;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.*;
import project1.shop.domain.repository.*;
import project1.shop.dto.innerDto.*;
import project1.shop.enumeration.PaymentStatus;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final ItemRepository itemRepository;
    private final OrdersRepository ordersRepository;
    private final IamportClient iamportClient;
    private final OrderDetailRepository orderDetailRepository;

    @Value("${imp.api.key}")
    private String apiKey;
    @Value("${imp.api.secret_key}")
    private String secretKey;



    // 주문 페이지 접속시 기본 세팅 데이터 응답
    @Transactional
    public OrderDto.OrderPageResponse giveOrderPage(Long id, List<OrderDto.OrderPageItemRequest> requestList) {

        // 판매중인 상품인지, 재고가 있는지 확인
        sellCheck(requestList);
        stockCheck(requestList);

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        Address address = addressRepository.searchMainAddress(id);
        AddressDto.OrderAddressResponse orderAddress = null;
        if (!(address == null)){
            orderAddress = new AddressDto.OrderAddressResponse(address);
        }

        List<ItemDto.OrderPageItemResponse> orderItems = new ArrayList<>();
        OrderDto.PriceAndPointResponse priceAndPoint = new OrderDto.PriceAndPointResponse();

        for(OrderDto.OrderPageItemRequest request : requestList){

            Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

            priceAndPoint.plusTotalPrice(item.getPrice());
            priceAndPoint.plusTotalDiscountPrice(item.getDiscountPrice());

            ItemDto.OrderPageItemResponse orderItem = new ItemDto.OrderPageItemResponse(item, request.getCount());

            orderItems.add(orderItem);
        }

        priceAndPoint.autoSetting(member);


        OrderDto.OrderPageResponse orderPageResponse = new OrderDto.OrderPageResponse(orderAddress, orderItems, priceAndPoint);


        return orderPageResponse;
    }


    // 1. 결제 버튼을 눌렀을 때 임시 order객체를 생성 및 저장 후 이니시스에게 전달해줄 DTO 생성 (+ orderDetail생성)(+ 가격 검증)
    @Transactional
    public PortOneDto.InicisResponse firstPayment(Long id, OrderDto.PaymentRequest request) {

        // 판매중인 상품인지, 재고가 있는지 확인
        sellCheck(request.getItems());
        stockCheck(request.getItems());

        // order객체에 저장할 결제 금액과 진짜 결제 금액 계산 검증
        int totalPaymentPrice = orderPriceCheck(request.getItems(), request.getUsePoint());
        if(!(request.getBuyPrice() == totalPaymentPrice)){
            throw new IllegalArgumentException("계산된 금액과 다른 주문 금액으로 주문 요청이 왔습니다.");
        }

        // 회원 보유 적립금 이하로 적립금 사용하고 있는지 검증
        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if(request.getUsePoint() > member.getPoint()){
            throw new IllegalArgumentException("보유한 적립금보다 많은 적립금을 사용하려고 합니다.");
        }

        // 사용한 적립금 차감
        member.deletePoint(request.getUsePoint());

        // orders 객체 생성
        Order order = new Order(member, request);
        ordersRepository.save(order);

        // orderDetail 객체 생성
        for(OrderDto.OrderPageItemRequest itemRequest : request.getItems()){

            Item item = itemRepository.findById(itemRequest.getItemId()).orElseThrow(IllegalArgumentException::new);

            OrderDetail orderDetail = new OrderDetail(order, item, itemRequest.getCount());

            orderDetailRepository.save(orderDetail);
        }

        // 이니시스에 전달할 DTO 생성
        PortOneDto.InicisResponse inicisDto = new PortOneDto.InicisResponse(order);

        return inicisDto;
    }


    // 2. 결제 완료시 결제를 검증하고 임시 orders객체를 완성 시키고 최종적으로 포트원에 저장된 결제 데이터를 가져와서 반환 (+ 상품 재고 차감)
    @Transactional
    public IamportResponse<Payment> validatePayment(Long id, PortOneDto.InicisRequest request) {

        try{
            // 결제 단건 조회
            IamportResponse<Payment> iamportResponse = getIamportResponse(request);

            // 해당 주문 테이블 테이블를 가져온다.
            Order order = ordersRepository.findByMerchantUid(request.getMerchantUid()).orElseThrow(NoSuchElementException::new);

            // 포트원으로부터 받은 결제 데이터 iamportResponse와 주문 데이터를 가져와서 결제에 대해 검증한다.
            validatePaymentStatusAndPay(iamportResponse, order);

            // 만약 결제 상태가 PYAMENT_DONE라면?(결제가 완료된 상태라면)
            if(PaymentStatus.PAYMENT_DONE.equals(order.getStatus())){

                log.info("이미 결제 완료된 주문입니다. imp_uid={}, merchant_uid={}", order.getImpUid(), order.getMerchantUid());

            } else {

                // 기존 orders 객체에 아임포트에서 가져온 imp_uid를 저장해준다. + 결제 상태 수정
                order.updateBySuccess(iamportResponse.getResponse().getImpUid());

                log.info("결제 완료 확인!, payment_uid={}, order_uid={}",
                        order.getImpUid(), order.getMerchantUid());
            }

            // 상품 재고 차감
            subStock(order);

            return iamportResponse;

        } catch (IamportResponseException | IOException e){
            throw new RuntimeException(e);
        }
    }


    // 내 주문 내역 목록 조회
    @Transactional
    public List<OrderDto.OrderResponse> showOrders(Long id, SearchDto.MyOrdersSearch request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 5);

        log.info("1");
        Page<Order> orders = ordersRepository.searchMyOrders(request, id, pageRequest);
        log.info("2");
        List<OrderDto.OrderResponse> ordersDto = orders.stream()
                .map(OrderDto.OrderResponse::new)
                .collect(Collectors.toList());

        log.info("3");
        for (OrderDto.OrderResponse orderDto : ordersDto){
            log.info("4");
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_OrderId(orderDto.getOrderId());
            log.info("5");
            List<OrderDetailDto.response> orderDetailsDto = orderDetails.stream()
                    .map(OrderDetailDto.response::new)
                    .collect(Collectors.toList());
            log.info("6");
            orderDto.plusOrderDetails(orderDetailsDto);
        }
        log.info("7");
        return ordersDto;
    }


    // 주문 상세 조회
    @Transactional
    public OrderDto.OrderDetailResponse showOrder(Long orderId) {

        Order order = ordersRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);

        OrderDto.OrderResponse orderResponse = new OrderDto.OrderResponse(order);

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);

        List<OrderDetailDto.response> orderDetailsDto = orderDetails.stream()
                .map(OrderDetailDto.response::new)
                .collect(Collectors.toList());

        orderResponse.plusOrderDetails(orderDetailsDto);



        OrderDto.OrderDetailResponse orderDetailResponse = new OrderDto.OrderDetailResponse(orderResponse, order);

        return orderDetailResponse;
    }


    // 결제 취소를 위해 토큰 발급 후 주문 취소하기
    @Transactional
    public void orderCancel(OrderDto.CancelRequest request) throws IOException {

        Order order = ordersRepository.findById(request.getOrderId()).orElseThrow(IllegalArgumentException::new);

        if(!order.getStatus().equals(PaymentStatus.PAYMENT_DONE)){

            throw new IllegalArgumentException("결제를 취소할 수 있는 상태가 아닙니다.");
        }


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


        // 데이터 수정하기
        // 주문 테이블 상태 변경하기
        PaymentStatus status = PaymentStatus.CANCEL;

        // 상품 재고 다시 되돌리기
        plusStock(order);

        // 결제 취소 사유 저장하기
        order.updateReasonText(request.getReasonText());
    }


    // 주문 상태 환불요청으로 변경하기
    @Transactional
    public void orderRefound(OrderDto.RefoundRequest request) {

        Order order = ordersRepository.findById(request.getOrderId()).orElseThrow(IllegalArgumentException::new);

        order.updateStatus(PaymentStatus.REFUND_REQUEST);
    }


    //----------------------------------------------------------------------------------------------


    // 아임포트에서 제공하는 iamportClient를 이용해서 IamportResponse타입인 아임포트 payment 객체 생성
    // 즉, 아임포트로부터 받은 결제 데이터를 가져온다.
    private IamportResponse<Payment> getIamportResponse(PortOneDto.InicisRequest request) throws IamportResponseException, IOException {

        IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(request.getImpUid());

        return iamportResponse;
    }



    // 포트원으로부터 받은 결제 데이터를 가지고 검증하는 메서드
    // 결제 완료가 되지 않음 -> 주문 정보 및 결제 정보 삭제
    // 결제 완료는 되었으나 결제 금액이 다름 -> 주문 정보 및 결제 정보 삭제 후, 포트원에 결제 취소 요청
    private void validatePaymentStatusAndPay(IamportResponse<Payment> iamportResponse, Order order) throws IamportResponseException, IOException{

        // 결제 완료 상태가 아니라면
        if(!iamportResponse.getResponse().getStatus().equals("paid")){

            // 해당 주문 상세 정보 삭제
            deleteOrderDetailsAndOrders(order);

            throw new RuntimeException("결제 미완료");
        }

        // 주문 정보에 있는 가격
        int price = order.getBuyPrice();

        // 포트원에 결제된 가격
        long portOnePrice = iamportResponse.getResponse().getAmount().longValue();

        // 주문 금액과 결제 금액이 동일한지 검증 (만약 다르다면)
        if(!Objects.equals(portOnePrice, price)){

            // 해당 주문 상세 정보와 주문 정보 삭제
            deleteOrderDetailsAndOrders(order);

            // 아임포트에게 결제를 취소를 요청하기 위해 결제 취소에 필요한 데이터를 포함하는 CancelData 객체를 생성한다.
            // 결제 고유 번호, 부분 취소 여부, 취소할 금액(결제된 가격)
            CancelData cancelData = new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(portOnePrice));

            // 결제 서비스 클라이언트(iamportClient)를 이용해서 결제 고유 번호를 기반으로 결제를 취소하는데 이때 취소에 필요한 정보가 있는 CancelDate 객체를 제공
            iamportClient.cancelPaymentByImpUid(cancelData);

            throw new RuntimeException("결제금액 상이하여 취소, 클라이언트 측의 위변조 가능성 있음");
        }

        log.info("결제 예정 금액과 결제한 금액이 동일함 -> 결제 완료 판단과 결제 금액이 정확한지 검증 완료");
    }




    // 결제되어야 하는 가격 계산
    public int orderPriceCheck(List<OrderDto.OrderPageItemRequest> items, int usePoint){

        int totalPaymentPrice = 0;

        for(OrderDto.OrderPageItemRequest request : items){

            Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

            totalPaymentPrice += item.getSellPrice() * request.getCount();
        }

        totalPaymentPrice -= usePoint;

        return totalPaymentPrice;
    }


    // 특정 orders와 해당 orderDetail를 DB에서 제거하기
    public void deleteOrderDetailsAndOrders(Order order){

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);

        for(OrderDetail orderDetail : orderDetails){

            orderDetailRepository.delete(orderDetail);
        }

        ordersRepository.delete(order);
    }


    // 상품 판매 여부 확인
    public void sellCheck(List<OrderDto.OrderPageItemRequest> requestList){

        for (OrderDto.OrderPageItemRequest request : requestList){

            Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

            if (!item.isSellCheck()){

                throw new IllegalArgumentException("판매중인 상품이 아닙니다.");
            }
        }
    }


    // 상품 재고 확인
    public void stockCheck(List<OrderDto.OrderPageItemRequest> requestList){

        for (OrderDto.OrderPageItemRequest request : requestList){

            Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

            if (item.getStock() < request.getCount()){

                throw new IllegalArgumentException("재고가 없습니다.");
            }
        }
    }


    // 상품 재고 차감
    public void subStock(Order order){

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);

        // 재고 확인
        for(OrderDetail orderDetail : orderDetails){

            Item item = itemRepository.findById(orderDetail.getItem().getItemId()).orElseThrow(IllegalArgumentException::new);

            // 만약 해당 상품의 재고보다 구매하려는 개수가 많다면?
            if(item.getStock() < orderDetail.getItemCount()){

                deleteOrderDetailsAndOrders(order);

                throw new IllegalArgumentException("재고가 부족합니다.");
            }
        }

        // 재고 차감
        for(OrderDetail orderDetail : orderDetails){

            Item item = itemRepository.findById(orderDetail.getItem().getItemId()).orElseThrow(IllegalArgumentException::new);

            item.subStock(orderDetail.getItemCount());
        }
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
