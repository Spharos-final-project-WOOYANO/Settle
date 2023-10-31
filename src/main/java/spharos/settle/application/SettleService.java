package spharos.settle.application;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.settle.dto.PaymentResultResponseList;

@Service
@Slf4j
public class SettleService implements SettleServiceImpl{


    @Override
    public void settle() {
        //1. payment service 에서 결제 완료된 결제 내역을 조회한다.
        //스케줄러 - payment(producer) -> settle(consumer) 메세지 발행 하고 consumer onmessage 에서 처리
        // settle이 consumer로 해서 topic 변경되면 변경한거 이후로확인

        //2. 각 결제 내역의 client_email 에 해당하는 계좌번호를 가져오고
        //스케줄러 - user(producer) -> settle(consumer) 메세지 발행 하고 consumer onmessage 에서 처리

        //3. 각 client_email 별로, 정산 금액을 계산해주고 with 수수료 처리

        //4. 계산된 금액을 계죄번호에 보냈는 true false 받는다
        //정산(producer) - client(consumer) 메세지 발행까지만 구현

        //5. 정산 완료된 결제 내역은 정산 완료 상태로 변경해준다.
        //settle(producer) -> payment(consumer) consumer-onmessage 에서 finishSettlement() 호출

        //6. 정산 완료 기록을 저장한다
    }


}
