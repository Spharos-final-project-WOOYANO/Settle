package spharos.settle.infrastructure;

import static spharos.settle.domain.settle.QDailySettle.dailySettle;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import spharos.settle.dto.DailySettleListResponse;
import spharos.settle.dto.QDailySettleListResponse;

public class DailySettleRepositoryImpl implements DailySettleRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public DailySettleRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<DailySettleListResponse> findBySettlementDateBetween(String clientEmail, LocalDate startDate,
                                                                      LocalDate endDate, Pageable pageable) {
        QueryResults<DailySettleListResponse> results = queryFactory
                .select(new QDailySettleListResponse(dailySettle.id, dailySettle.settlementDate,
                        dailySettle.payOutAmount, dailySettle.settleType))
                .from(dailySettle)
                .where(dailySettle.clientEmail.eq(clientEmail),
                        dailySettle.settlementDate.between(startDate, endDate))
                .orderBy(dailySettle.settlementDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<DailySettleListResponse> results1 = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(results1, pageable, total);

    }
}
