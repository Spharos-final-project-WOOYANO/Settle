package spharos.settle.domain.settle;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.settle.domain.payment.PaymentTypeConverter;

@Entity
@Getter
@Builder
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
    private Long totalAmount; //총 금액

//    @Column(name = "settle_Status")
//    @Convert(converter = SettleStatusConverter.class)
    @Column(name = "settle_Status")
    @Enumerated(EnumType.STRING)
    private SettleStatus settleType; //결제수단  카드, 간편결제

    private DailySettle(LocalDate settlementDate,String clientEmail, Long totalAmount, SettleStatus settleType) {
        this.settlementDate = settlementDate;
        this.clientEmail = clientEmail;
        this.totalAmount = totalAmount;
        this.settleType = settleType;
    }

    private DailySettle(LocalDate settlementDate,String clientEmail, Long totalAmount) {
        this.settlementDate = settlementDate;
        this.clientEmail = clientEmail;
        this.totalAmount = totalAmount;
    }
    public static DailySettle createSettle(String clientEmail, Long totalAmount, LocalDate settlementDate, SettleStatus settleType) {
        return new DailySettle(settlementDate, clientEmail, totalAmount, settleType);
    }
    public static DailySettle createSettle1(String clientEmail, Long totalAmount, LocalDate settlementDate) {
        return new DailySettle(settlementDate, clientEmail, totalAmount);
    }

    public void setTotalAmount(int plustotalAmount) {
        this.totalAmount += plustotalAmount;
    }
}
