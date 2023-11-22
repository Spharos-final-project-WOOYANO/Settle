package spharos.settle.presentation;

import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spharos.settle.application.SettleService;
import spharos.settle.batch.BatchScheduler;
import spharos.settle.domain.settle.DailySettle;
import spharos.settle.global.common.response.BaseResponse;
import spharos.settle.dto.DailySettleListResponse;
import spharos.settle.dto.DailySettleResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/settle")
public class DailySettleController {
    private final SettleService settleService;
    private final BatchScheduler batchScheduler;

   /* @GetMapping("/test")
    public  List<PaymentResult> test() {
        String requestDate = "2023-11-08";
        Map<String, Object> parameters = new LinkedHashMap<>();
        LocalDateTime parse = LocalDateTime.parse(requestDate + "T00:00:00");
        LocalDateTime parse1 = LocalDateTime.parse(requestDate + "T23:59:59");

        List<PaymentResult> test = settleService.test(parse,parse1);
        return  test;
        //       return paymentResultResponseLists;
    }*/
    //정산 리스트 조회
   @Operation(summary = "정산 리스트 조회", description = "정산 리스트 날짜 범위 설정해서 조회")
    @GetMapping("/list")
    public BaseResponse<?> getSettleList(@RequestParam String clientEmail,@RequestParam LocalDate startDate,
                                         @RequestParam LocalDate endDate,@PageableDefault Pageable pageable) {
       Page<DailySettleListResponse> settleInRange = settleService.getSettleInRange(clientEmail, startDate, endDate, pageable);
       return new BaseResponse<>(settleInRange);
    }
    //정산 리스트 조회 시 총 정산 금액
    @GetMapping("/totalAmount")
    public BaseResponse<?> getTotal(@RequestParam String clientEmail,@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        Long total = settleService.sumTotalAmountByClientEmailAndSettlementDate(clientEmail, startDate, endDate);
        return new BaseResponse<>(total);
    }
    //정산 리스트에서 단건 조회
    @GetMapping("/{id}")
    public BaseResponse<?> getSettle(@PathVariable Long id) {
        DailySettleResponse settle = settleService.getSettle(id);
        return new BaseResponse<>(settle);
    }

    @GetMapping("/test")
    public void test(){
       batchScheduler.runJob();
    }



}
