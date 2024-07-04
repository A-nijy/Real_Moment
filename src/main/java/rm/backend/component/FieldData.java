package rm.backend.component;


import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FieldData {

    private int orderCount = 0;


    // 매일 1로 초기화
    public void resetCount() {
        orderCount = 0;
    }

    // 주문시 1씩 증가
    public void countUp() {
        orderCount++;
    }
}
