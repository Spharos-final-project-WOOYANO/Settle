package spharos.settle.presentation;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spharos.settle.application.SettleService;
import spharos.settle.domain.payment.Payment;
import spharos.settle.dto.PaymentResult;
import spharos.settle.dto.PaymentResultResponseList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/settle")
public class SettleController {
    private final SettleService settleService;

    @GetMapping("/test")
    public  List<PaymentResult> test() {
        String requestDate = "2023-11-08";
        Map<String, Object> parameters = new LinkedHashMap<>();
        LocalDateTime parse = LocalDateTime.parse(requestDate + "T00:00:00");
        LocalDateTime parse1 = LocalDateTime.parse(requestDate + "T23:59:59");

        List<PaymentResult> test = settleService.test(parse,parse1);
        return  test;
        //       return paymentResultResponseLists;
    }

}
