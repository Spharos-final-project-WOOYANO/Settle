package spharos.settle.domain.settle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED) //NoArgsConstru
public class MonthlySettle {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "start_Date")
    private LocalDateTime startDate; //정산 시작일
    @Column(nullable = false,name = "end_Date")
    private LocalDateTime endDate; //정산 종료일


    @Column(nullable = false,name = "client_Email")
    private String clientEmail; //사업자 이메일
    @Column(nullable = false,name = "total_Amount")
    private int totalAmount; //총 금액

    private MonthlySettle(LocalDateTime startDate, LocalDateTime endDate, String clientEmail, int totalAmount) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.clientEmail = clientEmail;
        this.totalAmount = totalAmount;
    }

    public static MonthlySettle createSettle(String clientEmail, int totalAmount, LocalDateTime startDate, LocalDateTime endDate) {
        return new MonthlySettle(startDate, endDate, clientEmail, totalAmount);
    }
}
