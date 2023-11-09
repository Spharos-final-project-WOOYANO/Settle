package spharos.settle.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DbAppConfig {
    /* JPA 관련 설정 */
    @PersistenceContext(unitName = "db1EntityManagerFactory")
    private EntityManager primaryEntityManager;

    @PersistenceContext(unitName = "db2EntityManagerFactory")
    private EntityManager secondEntityManager;

    /* QueryDsl 관련 설정 */

 /*   @Bean
    public JPAQueryFactory primaryQueryFactory() {
        return new JPAQueryFactory(primaryEntityManager);
    }

    @Bean
    public JPAQueryFactory rawDataQueryFactory() {
        return new JPAQueryFactory(secondEntityManager);
    }*/
}
