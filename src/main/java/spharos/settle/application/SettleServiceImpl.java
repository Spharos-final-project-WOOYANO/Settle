package spharos.settle.application;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spharos.settle.domain.settle.DailySettle;
import spharos.settle.dto.DailySettleListResponse;
import spharos.settle.dto.DailySettleResponse;

public interface SettleServiceImpl {
    DailySettleResponse getSettle(Long id);

    Page<DailySettleListResponse> getSettleInRange(String clientEmail,LocalDate startDate, LocalDate endDate, Pageable pageable);
    Long sumTotalAmountByClientEmailAndSettlementDate(String clientEmail, LocalDate startDate, LocalDate endDate );
   // List<PaymentResultResponseList> processPaymentResult(ConsumerRecord<String, String> consumerRecord)throws JsonProcessingException;

}
