package spharos.settle.infrastructure.payment;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.query.Param;
import spharos.settle.domain.payment.Payment;
import spharos.settle.domain.payment.PaymentStatus;
import spharos.settle.dto.PaymentResultResponseList;

public interface PaymentRepositoryCustom {
    List<PaymentResultResponseList> findByApprovedAtAndPaymentStatus(LocalDateTime startDate, LocalDateTime endDate,
                                                                     PaymentStatus doneStatus, PaymentStatus cancelStatus);
}
