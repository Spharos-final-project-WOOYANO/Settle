package spharos.settle.application;

import java.time.LocalDate;
import java.util.List;
import spharos.settle.domain.settle.DailySettle;
import spharos.settle.dto.DailySettleListResponse;
import spharos.settle.dto.DailySettleResponse;

public interface SettleServiceImpl {
    DailySettleResponse getSettle(Long id);
    List<DailySettle> getList();
    List<DailySettleListResponse> getSettleInRange(LocalDate startDate, LocalDate endDate);
   // List<PaymentResultResponseList> processPaymentResult(ConsumerRecord<String, String> consumerRecord)throws JsonProcessingException;

}
