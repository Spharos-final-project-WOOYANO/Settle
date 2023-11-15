package spharos.settle.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailySettleListResponse {

    private Long id; //정산 아이디

    private LocalDate settlementDate; //정산 시작일

    private long payOutAmount; //단건 정산 금액

    private String settleType; //정산 상태 (정산 완료, 정산 예정)


}
