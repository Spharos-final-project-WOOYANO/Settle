package spharos.settle.domain.settle;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.settle.domain.payment.PaymentTypeConverter;

@Entity
@Getter
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
    private int totalAmount; //총 금액

    @Column(nullable = false,name = "settle_Status")
    @Convert(converter = SettleStatusConverter.class)
    private SettleStatus settleType; //결제수단  카드, 간편결제

    private DailySettle(LocalDate settlementDate,String clientEmail, int totalAmount, SettleStatus settleType) {
        this.settlementDate = settlementDate;
        this.clientEmail = clientEmail;
        this.totalAmount = totalAmount;
        this.settleType = settleType;
    }

    public static DailySettle createSettle(String clientEmail, int totalAmount, LocalDate settlementDate, SettleStatus settleType) {
        return new DailySettle(settlementDate, clientEmail, totalAmount, settleType);
    }
}
