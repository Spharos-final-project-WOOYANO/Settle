package spharos.settle.infrastructure.payment;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spharos.settle.domain.payment.Payment;
import spharos.settle.domain.payment.PaymentStatus;
import spharos.settle.dto.PaymentResult;

public interface PaymentRepository extends JpaRepository<Payment, Long> , PaymentRepositoryCustom{


/*    @Query("SELECT p FROM Payment p WHERE p.approvedAt BETWEEN :startDate AND :endDate " +
            "AND (p.paymentStatus = :paymentStatus1 OR p.paymentStatus = :paymentStatus2)")
    List<Payment> findByApprovedAtAndPaymentStatus(@Param("startDate") LocalDateTime startDate,
                                                   @Param("endDate") LocalDateTime endDate,
                                                   @Param("paymentStatus1") PaymentStatus doneStatus,
                                                   @Param("paymentStatus2") PaymentStatus cancelStatus);*/
    @Query("SELECT new spharos.settle.dto.PaymentResult(p.clientEmail,SUM(p.totalAmount)) FROM Payment p "
            + "WHERE p.approvedAt BETWEEN :startDate AND :endDate group by p.clientEmail" )
    List<PaymentResult> teset(@Param("startDate") LocalDateTime startDate,
                              @Param("endDate") LocalDateTime endDate);



}



