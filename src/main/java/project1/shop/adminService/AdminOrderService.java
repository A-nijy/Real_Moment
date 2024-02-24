package project1.shop.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project1.shop.domain.repository.OrdersRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminOrderService {

    private final OrdersRepository ordersRepository;



}
