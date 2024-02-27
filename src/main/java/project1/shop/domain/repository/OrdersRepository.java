package project1.shop.domain.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import project1.shop.domain.entity.Order;
import project1.shop.domain.repository.custom.OrdersRepositoryCustom;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Order, Long>, OrdersRepositoryCustom {

    Optional<Order> findByMerchantUid(String merchantUid);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Order> findLockByMerchantUid(String merchantUid);


}
