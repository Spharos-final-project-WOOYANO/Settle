package spharos.settle.application;

import static java.util.stream.Collectors.summingInt;
import static spharos.settle.domain.payment.PaymentStatus.CANCEL;
import static spharos.settle.domain.payment.PaymentStatus.DONE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spharos.settle.domain.payment.Payment;
import spharos.settle.domain.settle.DailySettle;
import spharos.settle.domain.settle.SettleStatus;
import spharos.settle.infrastructure.payment.PaymentRepository;
import spharos.settle.dto.PaymentResultResponseList;
import spharos.settle.infrastructure.settle.DailySettleRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class SettleService {

    private final PaymentRepository paymentRepository;
    private final DailySettleRepository dailySettleRepository;

    // 결제 db직접 연결해서 정산
    // 입금 요청상태
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정 시작
    public void settle() {
        LocalDateTime currentDateTime = LocalDateTime.now(); // 현재 날짜와 시간
        // 전날 날짜의 자정 00:00:00
        LocalDateTime startOfPreviousDate = currentDateTime.minusDays(1).withHour(0).withMinute(0).withSecond(0);
        // 전날 날짜의 23:59:59
        LocalDateTime endOfPreviousDate = currentDateTime.minusDays(1).withHour(23).withMinute(59).withSecond(59);
        LocalDate settlementDate = currentDateTime.toLocalDate().minusDays(1);
        log.info("lastWednesdayMidnight : {}", startOfPreviousDate);
        log.info("lastTuesdayMidnight : {}", endOfPreviousDate);

        List<PaymentResultResponseList> filteredPaymentResults = paymentRepository.findByApprovedAtAndPaymentStatus(
                startOfPreviousDate, endOfPreviousDate, DONE, CANCEL);

        //clientEmail별로 총 결제 금액 계산
        Map<String, Integer> totalAmountByClientEmail = calculateTotalAmountByClientEmail(filteredPaymentResults);

        //날짜별로 정산 결과 저장
        saveTotalAmountByClientEmail(totalAmountByClientEmail, settlementDate);

    }

    //조회 전주 수요일 부터
    private LocalDateTime calculateLastWednesdayMidnight(LocalDateTime currentDateTime) {
        int daysUntilWednesday = DayOfWeek.WEDNESDAY.getValue() - currentDateTime.getDayOfWeek().getValue();
        if (daysUntilWednesday < 0) {
            daysUntilWednesday += 7;
        }
        return currentDateTime.minusDays(daysUntilWednesday).withHour(0).withMinute(0).withSecond(0);
    }
    //조회 이번주 화요일까지
    private LocalDateTime calculateLastTuesdayMidnight(LocalDateTime currentDateTime) {
        return currentDateTime.minusDays(1).withHour(23).withMinute(59).withSecond(59);
    }
    //clientEmail별로 총 결제 금액 계산
    private  Map<String, Integer> calculateTotalAmountByClientEmail(List<PaymentResultResponseList> filteredPaymentResults) {
        return filteredPaymentResults.stream().collect(
                Collectors.groupingBy(PaymentResultResponseList::getClientEmail,
                        summingInt(PaymentResultResponseList::getTotalAmount)));
    }
    //settle에 clientEmail별로 총 결제 금액 저장

    private void saveTotalAmountByClientEmailToSettle(Map<String, Integer> totalAmountByClientEmail,
                                                      LocalDate settlementDate) {
        double feeRate = 0.98;
        totalAmountByClientEmail.forEach((clientEmail, totalAmount) -> {
            double adjustedTotalAmount = totalAmount * feeRate;
            int intTotalAmount = (int) adjustedTotalAmount;
            SettleStatus depositScheduled = SettleStatus.DEPOSIT_SCHEDULED;
            DailySettle settle = DailySettle.createSettle(clientEmail, intTotalAmount, settlementDate,depositScheduled);
            dailySettleRepository.save(settle);
        });
    }


    private void saveTotalAmountByClientEmail(Map<String, Integer> totalAmountByClientEmail,
                                                      LocalDate settlementDate) {
        double feeRate = 0.98;
        List<DailySettle> settleList = new ArrayList<>();
        totalAmountByClientEmail.forEach((clientEmail, totalAmount) -> {
            double adjustedTotalAmount = totalAmount * feeRate;
            int intTotalAmount = (int) adjustedTotalAmount;
            SettleStatus depositScheduled = SettleStatus.DEPOSIT_SCHEDULED;
            DailySettle settle = DailySettle.createSettle(clientEmail, intTotalAmount, settlementDate, depositScheduled);
            settleList.add(settle);
        });
        dailySettleRepository.saveAll(settleList);
    }


}
