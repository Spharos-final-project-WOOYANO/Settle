package spharos.settle.infrastructure;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spharos.settle.domain.settle.DailySettle;

public interface DailySettleRepository extends JpaRepository<DailySettle, Long>,DailySettleRepositoryCustom {
    Optional<DailySettle> findByClientEmail(String clientEmail);

   /* @Query("SELECT d FROM DailySettle d WHERE d.clientEmail= :clientEmail and d.settlementDate between :startDate AND :endDate")
    List<DailySettle> findBySettlementDateBetween( @Param("clientEmail") String clientEmail,
                                                   @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);*/

    @Query("SELECT SUM(d.totalAmount) as totalAmountSum FROM DailySettle d WHERE d.clientEmail= :clientEmail and d.settlementDate between :startDate AND :endDate group by d.clientEmail")
    Long sumTotalAmountByClientEmailAndSettlementDate(@Param("clientEmail") String clientEmail,
                                                      @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
