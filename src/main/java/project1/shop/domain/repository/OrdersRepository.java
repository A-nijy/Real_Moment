package project1.shop.domain.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import project1.shop.domain.entity.Orders;
import project1.shop.domain.repository.custom.OrdersRepositoryCustom;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long>, OrdersRepositoryCustom {

    Optional<Orders> findByMerchantUid(String merchantUid);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Orders> findLockByMerchantUid(String merchantUid);


}
