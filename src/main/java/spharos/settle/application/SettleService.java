package spharos.settle.application;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import spharos.settle.domain.settle.DailySettle;
import spharos.settle.dto.DailySettleListResponse;
import spharos.settle.dto.DailySettleResponse;
import spharos.settle.infrastructure.DailySettleRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class SettleService implements SettleServiceImpl {

    private final DailySettleRepository dailySettleRepository;

    public Page<DailySettleListResponse> getSettleInRange(String clientEmail, LocalDate startDate, LocalDate endDate,
                                                          Pageable pageable) {

        return dailySettleRepository.findBySettlementDateBetween(clientEmail, startDate, endDate, pageable);

    }

    public DailySettleResponse getSettle(Long id){
        DailySettle dailySettle = dailySettleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("정산 내역이 없습니다."));
        return DailySettleResponse.builder()
                .settleType(dailySettle.getSettleType())
                .totalAmount(dailySettle.getTotalAmount())
                .fee(dailySettle.getFee())
                .payOutAmount(dailySettle.getPayOutAmount())
                .settlementDate(dailySettle.getSettlementDate())
                .build();
    }



    public Long sumTotalAmountByClientEmailAndSettlementDate(String clientEmail, LocalDate startDate, LocalDate endDate){
        return dailySettleRepository.sumTotalAmountByClientEmailAndSettlementDate(clientEmail,startDate,endDate);
    }



   /* // 결제 db직접 연결해서 정산
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
          //  int intTotalAmount = (int) adjustedTotalAmount;
            long intTotalAmount = (long) adjustedTotalAmount;
            SettleStatus depositScheduled = SettleStatus.DEPOSIT_SCHEDULED;
            DailySettle settle = DailySettle.createSettle(clientEmail, intTotalAmount, settlementDate,depositScheduled,0l,0l);
            dailySettleRepository.save(settle);
        });
    }


    private void saveTotalAmountByClientEmail(Map<String, Integer> totalAmountByClientEmail,
                                                      LocalDate settlementDate) {
        double feeRate = 0.98;
        List<DailySettle> settleList = new ArrayList<>();
        totalAmountByClientEmail.forEach((clientEmail, totalAmount) -> {
            double adjustedTotalAmount = totalAmount * feeRate;
            //  int intTotalAmount = (int) adjustedTotalAmount;
            long intTotalAmount = (long) adjustedTotalAmount;
            SettleStatus depositScheduled = SettleStatus.DEPOSIT_SCHEDULED;
            DailySettle settle = DailySettle.createSettle(clientEmail, intTotalAmount, settlementDate, depositScheduled,0l,0l);
            settleList.add(settle);
        });
        dailySettleRepository.saveAll(settleList);
    }
    public List<PaymentResult> test(LocalDateTime a, LocalDateTime b){
        return paymentRepository.teset(a,b);
    }*/



}
