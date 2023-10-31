package spharos.settle.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PaymentResultResponseList {

    private String clientEmail; //사업자 이메일

    private int totalAmount; //있

    private LocalDateTime approvedAt; //있

}