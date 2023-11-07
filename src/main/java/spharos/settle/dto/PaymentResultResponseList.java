package spharos.settle.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentResultResponseList {

    private String clientEmail; //사업자 이메일

    private int totalAmount; //있

    private LocalDateTime approvedAt; //있

    @QueryProjection
    public PaymentResultResponseList(String clientEmail, int totalAmount, LocalDateTime approvedAt) {
        this.clientEmail = clientEmail;
        this.totalAmount = totalAmount;
        this.approvedAt = approvedAt;
    }
}