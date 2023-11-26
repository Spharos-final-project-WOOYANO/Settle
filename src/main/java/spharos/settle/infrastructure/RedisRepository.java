package spharos.settle.infrastructure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spharos.settle.domain.settle.DailySettle;

@Repository
public interface RedisRepository extends CrudRepository<DailySettle,Long> {
}
