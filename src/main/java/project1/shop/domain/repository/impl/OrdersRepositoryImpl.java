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
    public Page<Orders> searchMyOrders(SearchDto.MyOrdersSearch searchDto, Long memberId, Pageable pageable) {

        // Orders엔티티에 OrderDetail를 참조하고 있지 않기 때문에 바로 Item에 접근 불가..
        // 그래서 먼저 특정 상품 이름을 포함하는 OrderDetail 목록을 조회해서 해당 OrderId들을 뽑아옴
        List<Long> orderIds = queryFactory.select(QOrderDetail.orderDetail.orders.orderId)
                .from(QOrderDetail.orderDetail)
                .join(QOrderDetail.orderDetail.item, QItem.item)
                .where(itemNameContains(searchDto.getItemName()))
                .fetch();

        log.info(orderIds.toString());

        List<Orders> orders = queryFactory.selectFrom(QOrders.orders)
                .where(orderIdIn(orderIds),
                        statusEq(searchDto.getStatus()),
                        betweenDate(searchDto.getStartDate(), searchDto.getLastDate()),
                        QOrders.orders.member.memberId.eq(memberId))
                .orderBy(QOrders.orders.orderedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        log.info(orderIds.toString());
        Long total = queryFactory
                .select(QOrders.orders.count())
                .from(QOrders.orders)
                .where(orderIdIn(orderIds),
                        statusEq(searchDto.getStatus()),
                        betweenDate(searchDto.getStartDate(), searchDto.getLastDate()),
                        QOrders.orders.member.memberId.eq(memberId))
                .fetchOne();


        return new PageImpl<>(orders, pageable, total);

    }


    @Override
    public Page<Orders> searchOrders(SearchDto.OrdersSearch searchDto, Pageable pageable) {

        // Orders엔티티에 OrderDetail를 참조하고 있지 않기 때문에 바로 Item에 접근 불가..
        // 그래서 먼저 특정 상품 이름을 포함하는 OrderDetail 목록을 조회해서 해당 OrderId들을 뽑아옴
        List<Long> orderIds = queryFactory.select(QOrderDetail.orderDetail.orders.orderId)
                .from(QOrderDetail.orderDetail)
                .join(QOrderDetail.orderDetail.item, QItem.item)
                .where(itemNameContains(searchDto.getItemName()))
                .fetch();

        log.info(orderIds.toString());

        List<Orders> orders = queryFactory.selectFrom(QOrders.orders)
                .where(orderIdIn(orderIds),
                        statusEq(searchDto.getStatus()),
                        betweenDate(searchDto.getStartDate(), searchDto.getLastDate()),
                        loginIdContains(searchDto.getLoginId()))
                .orderBy(QOrders.orders.orderedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Long total = queryFactory
                .select(QOrders.orders.count())
                .from(QOrders.orders)
                .where(orderIdIn(orderIds),
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
        return QOrders.orders.orderId.in(orderIds);
    }

    private BooleanExpression loginIdContains(String loginId) {

        if(loginId == null){
            return null;
        }
        return loginId.isEmpty() ? null : QOrders.orders.member.loginId.eq(loginId);
    }

    private BooleanExpression statusEq(PaymentStatus status){

        if(status == null){
            return null;
        }

        switch (status) {

            case PAYMENT_READY:
                return QOrders.orders.status.eq(PaymentStatus.PAYMENT_READY);

            case PAYMENT_DONE:
                return QOrders.orders.status.eq(PaymentStatus.PAYMENT_DONE);

            case DELIVERY_READY:
                return QOrders.orders.status.eq(PaymentStatus.DELIVERY_READY);

            case DELIVERY_DOING:
                return QOrders.orders.status.eq(PaymentStatus.DELIVERY_DOING);

            case DELIVERY_DONE:
                return QOrders.orders.status.eq(PaymentStatus.DELIVERY_DONE);

            case CANCEL:
                return QOrders.orders.status.eq(PaymentStatus.CANCEL);

            case REFUND_REQUEST:
                return QOrders.orders.status.eq(PaymentStatus.REFUND_REQUEST);

            case REFUND_DONE:
                return QOrders.orders.status.eq(PaymentStatus.REFUND_DONE);

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

        return QOrders.orders.orderedDate.between(startDate, lastDate);
    }
}
