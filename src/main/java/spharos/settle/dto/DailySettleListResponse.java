package spharos.settle.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DailySettleListResponse {

    private Long id; //정산 아이디

    private LocalDate settlementDate; //정산 시작일

    private long payOutAmount; //단건 정산 금액

    private String settleType; //정산 상태 (정산 완료, 정산 예정)

    @QueryProjection
    public DailySettleListResponse(Long id, LocalDate settlementDate, long payOutAmount, String settleType) {
        this.id = id;
        this.settlementDate = settlementDate;
        this.payOutAmount = payOutAmount;
        this.settleType = settleType;
    }

}
