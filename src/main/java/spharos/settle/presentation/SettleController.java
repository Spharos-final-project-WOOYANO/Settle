package spharos.settle.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spharos.settle.application.SettleService;
import spharos.settle.dto.PaymentResultResponseList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/settle")
public class SettleController {
    private final SettleService settleService;

    @GetMapping("/test")
    public String test() {
        return "settle test";
    }

}
