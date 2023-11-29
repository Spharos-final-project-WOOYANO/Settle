package spharos.settle.domain.settle;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "daily_settle")
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED) //NoArgsConstru
public class DailySettle {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "start_Date")
    private LocalDate settlementDate; //정산 시작일

    @Column(nullable = false,name = "client_Email")
    private String clientEmail; //사업자 이메일
    @Column(nullable = false,name = "total_Amount")
    private long totalAmount; //총 결제 금액

    @Column(nullable = false,name = "fee")
    private long fee; //수수료

    @Column(nullable = false,name = "pay_Out_Amount")
    private long payOutAmount; //정산 지급금액


    @Column(name = "settle_Status")
    private String settleType; //정산 상태 (정산 완료, 정산 예정)

    private DailySettle(LocalDate settlementDate,String clientEmail, Long totalAmount, String settleType
    ,Long fee,Long payOutAmount) {
        this.settlementDate = settlementDate;
        this.clientEmail = clientEmail;
        this.totalAmount = totalAmount;
        this.settleType = settleType;
        this.fee = fee;
        this.payOutAmount = payOutAmount;
    }

    public static DailySettle createSettle(String clientEmail, Long totalAmount, LocalDate settlementDate,
                                           String settleType,Long fee,Long payOutAmount) {
        return new DailySettle(settlementDate, clientEmail, totalAmount, settleType,fee,payOutAmount);
    }

}
