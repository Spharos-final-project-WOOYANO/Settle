package spharos.settle.domain.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "client_Email")
    private String clientEmail; //사업자 이메일
    @Column(nullable = false,name = "payment_Type")
    @Convert(converter = PaymentTypeConverter.class)
    private PaymentType paymentType; //결제수단  카드, 간편결제

    @Column(nullable = false,name = "total_Amount")
    private int totalAmount; //결제 금액

    @Column(nullable = false,name = "approved_At")
    private LocalDateTime approvedAt; //결제 완료,취소가 일어난 날짜와 시간 정보

    @Column(nullable = false,name = "payment_Status")
    @Convert(converter = PaymentStatusConverter.class)
    private PaymentStatus paymentStatus; //결제 완료, 취소, 정산완료


    private Payment(String clientEmail, PaymentType payType, int totalAmount,
                    LocalDateTime approvedAt, PaymentStatus payStatus) {
        this.clientEmail = clientEmail;
        this.paymentType = payType;
        this.totalAmount = totalAmount;
        this.approvedAt = approvedAt;
        this.paymentStatus = payStatus;
    }

    public static Payment createPayment(String clientEmail, PaymentType payType, int totalAmount,
                                        LocalDateTime approvedAt, PaymentStatus paymentStatus) {
        return new Payment(clientEmail, payType,
                totalAmount, approvedAt,paymentStatus);
    }

    public void finishSettlement(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void updatePaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
