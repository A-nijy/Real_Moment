package project1.shop.domain.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.*;
import project1.shop.domain.repository.custom.OrdersRepositoryCustom;
import project1.shop.dto.innerDto.SearchDto;
import project1.shop.enumeration.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class OrdersRepositoryImpl implements OrdersRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public OrdersRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }


    @Override
    public Page<Order> searchMyOrders(SearchDto.MyOrdersSearch searchDto, Long memberId, Pageable pageable) {

        // Orders엔티티에 OrderDetail를 참조하고 있지 않기 때문에 바로 Item에 접근 불가..
        // 그래서 먼저 특정 상품 이름을 포함하는 OrderDetail 목록을 조회해서 해당 OrderId들을 뽑아옴
        List<Long> orderIds = queryFactory.select(QOrderDetail.orderDetail.order.orderId)
                .from(QOrderDetail.orderDetail)
                .join(QOrderDetail.orderDetail.item, QItem.item)
                .where(itemNameContains(searchDto.getItemName()))
                .fetch();

        log.info(orderIds.toString());

        List<Order> orders = queryFactory.selectFrom(QOrder.order)
                .where(orderIdIn(orderIds),
                        statusEq(searchDto.getStatus()),
                        betweenDate(searchDto.getStartDate(), searchDto.getLastDate()),
                        QOrder.order.member.memberId.eq(memberId))
                .orderBy(QOrder.order.orderedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        log.info(orderIds.toString());
        Long total = queryFactory
                .select(QOrder.order.count())
                .from(QOrder.order)
                .where(orderIdIn(orderIds),
                        statusEq(searchDto.getStatus()),
                        betweenDate(searchDto.getStartDate(), searchDto.getLastDate()),
                        QOrder.order.member.memberId.eq(memberId))
                .fetchOne();


        return new PageImpl<>(orders, pageable, total);

    }


    @Override
    public Page<Order> searchOrders(SearchDto.OrdersSearch searchDto, Pageable pageable) {

        // Orders엔티티에 OrderDetail를 참조하고 있지 않기 때문에 바로 Item에 접근 불가..
        // 그래서 먼저 특정 상품 이름을 포함하는 OrderDetail 목록을 조회해서 해당 OrderId들을 뽑아옴
        List<Long> orderIds = queryFactory.select(QOrderDetail.orderDetail.order.orderId)
                .from(QOrderDetail.orderDetail)
                .join(QOrderDetail.orderDetail.item, QItem.item)
                .where(itemNameContains(searchDto.getItemName()))
                .fetch();

        log.info(orderIds.toString());

        List<Order> orders = queryFactory.selectFrom(QOrder.order)
                .where(orderIdIn(orderIds),
                        merchantUidContains(searchDto.getMerchantUid()),
                        statusEq(searchDto.getStatus()),
                        betweenDate(searchDto.getStartDate(), searchDto.getLastDate()),
                        loginIdContains(searchDto.getLoginId()))
                .orderBy(QOrder.order.orderedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Long total = queryFactory
                .select(QOrder.order.count())
                .from(QOrder.order)
                .where(orderIdIn(orderIds),
                        merchantUidContains(searchDto.getMerchantUid()),
                        statusEq(searchDto.getStatus()),
                        betweenDate(searchDto.getStartDate(), searchDto.getLastDate()),
                        loginIdContains(searchDto.getLoginId()))
                .fetchOne();


        return new PageImpl<>(orders, pageable, total);

    }


    //---------------------------------------------

    private BooleanExpression itemNameContains(String itemName) {

        if(itemName == null){
            return null;
        }
        return itemName.isEmpty() ? null : QItem.item.name.contains(itemName);
    }

    private BooleanExpression orderIdIn(List<Long> orderIds){

        if(orderIds == null){
            return null;
        }
        return QOrder.order.orderId.in(orderIds);
    }

    private BooleanExpression loginIdContains(String loginId) {

        if(loginId == null){
            return null;
        }
        return loginId.isEmpty() ? null : QOrder.order.member.loginId.contains(loginId);
    }

    private BooleanExpression statusEq(PaymentStatus status){

        if(status == null){
            return null;
        }

        switch (status) {

            case PAYMENT_READY:
                return QOrder.order.status.eq(PaymentStatus.PAYMENT_READY);

            case PAYMENT_DONE:
                return QOrder.order.status.eq(PaymentStatus.PAYMENT_DONE);

            case DELIVERY_READY:
                return QOrder.order.status.eq(PaymentStatus.DELIVERY_READY);

            case DELIVERY_DOING:
                return QOrder.order.status.eq(PaymentStatus.DELIVERY_DOING);

            case DELIVERY_DONE:
                return QOrder.order.status.eq(PaymentStatus.DELIVERY_DONE);

            case CANCEL:
                return QOrder.order.status.eq(PaymentStatus.CANCEL);

            case REFUND_REQUEST:
                return QOrder.order.status.eq(PaymentStatus.REFUND_REQUEST);

            case REFUND_DONE:
                return QOrder.order.status.eq(PaymentStatus.REFUND_DONE);

            case DONE:
                return QOrder.order.status.eq(PaymentStatus.DONE);

            default:
                return null;
        }
    }


    // startDate ~ LastDate
    private BooleanExpression betweenDate(LocalDateTime startDate, LocalDateTime lastDate){

        if (lastDate == null){
            lastDate = LocalDateTime.now();
        }
        if(startDate == null){
            startDate = lastDate.minusMonths(3);
        }

        return QOrder.order.orderedDate.between(startDate, lastDate);
    }


    // 주문 번호 검색
    private BooleanExpression merchantUidContains(String merchantUid){

        if(merchantUid == null){
            return null;
        }

        return merchantUid.isEmpty() ? null : QOrder.order.merchantUid.contains(merchantUid);
    }
}
