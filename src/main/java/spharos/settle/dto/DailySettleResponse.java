package spharos.settle.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailySettleResponse {

    private LocalDate settlementDate; //정산 시작일

    private long totalAmount; //총 결제 금액
    private long fee; //총 수수료
    private long payOutAmount; //정산 지급금액

    private String settleType; //정산 상태 (정산 완료, 정산 예정)

}
