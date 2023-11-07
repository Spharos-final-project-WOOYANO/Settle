package spharos.settle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankInfo {
    String bankName; //은행명
    String bankHolder; //예금주
    String bankAccountNumber; //계좌번호
    Integer totalAmount;//총 금액
}
