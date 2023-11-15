package spharos.settle.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResult {
    private String clientEmail; //사업자 이메일

    private long totalAmount; //있


}