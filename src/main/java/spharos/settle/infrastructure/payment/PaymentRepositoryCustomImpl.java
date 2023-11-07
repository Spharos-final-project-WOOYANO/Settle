package spharos.settle.infrastructure.payment;

import static spharos.settle.domain.payment.QPayment.payment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import spharos.settle.domain.payment.PaymentStatus;
import spharos.settle.dto.PaymentResultResponseList;
import spharos.settle.dto.QPaymentResultResponseList;

public class PaymentRepositoryCustomImpl implements PaymentRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    public PaymentRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PaymentResultResponseList> findByApprovedAtAndPaymentStatus(LocalDateTime startDate,
                                                                            LocalDateTime endDate,
                                                                            PaymentStatus doneStatus,
                                                                            PaymentStatus cancelStatus) {
        return queryFactory.select(new QPaymentResultResponseList(payment.clientEmail,payment.totalAmount,payment.approvedAt))
                        .from(payment)
                .where(payment.approvedAt.between(startDate, endDate),
                        payment.paymentStatus.eq(doneStatus).or(payment.paymentStatus.eq(cancelStatus))).fetch();
}}
