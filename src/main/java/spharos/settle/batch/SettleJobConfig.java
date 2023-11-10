package spharos.settle.batch;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import spharos.settle.batch.expression.Expression;
import spharos.settle.batch.processor.PaymentItemProcessor;
import spharos.settle.batch.reader.QuerydslNoOffsetIdPagingItemReader;
import spharos.settle.batch.reader.QuerydslNoOffsetNumberOptions;
import spharos.settle.batch.reader.QuerydslNoOffsetPagingItemReader;
import spharos.settle.batch.reader.QuerydslPagingItemReader;
import spharos.settle.batch.writer.SettleItemWriter;
import spharos.settle.domain.payment.Payment;
import spharos.settle.dto.PaymentResult;
import spharos.settle.dto.PaymentResultResponseList;


import static spharos.settle.domain.payment.QPayment.payment;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SettleJobConfig {

    @Qualifier("db1EntityManagerFactory")
    private final EntityManagerFactory entityManagerFactory;
    @Autowired
    LocalContainerEntityManagerFactoryBean readerEntityManagerFactory;


    @Qualifier("db1TransactionManager")
    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;

    private final static int CHUNK_SIZE = 10;
    private final PaymentItemProcessor paymentItemProcessor;
    private final SettleItemWriter settleItemWriter;

    @Bean
    public Job createJob() {
        return new JobBuilder("settleJob19", jobRepository)
                .validator(new CustomJobParameterValidator())
                .start(settleStep())
                .build();
    }

    @Bean
    @JobScope
    public Step settleStep() {
        return new StepBuilder("settleStep", jobRepository)
                .<PaymentResult,PaymentResult>chunk(CHUNK_SIZE,transactionManager) // Chunk 크기를 지정
                .reader(reader2())
               // .processor(paymentItemProcessor)
                .writer(settleItemWriter)
           //     .transactionManager(BeanUtils.getTransactionManagerBean(2))
                .build();

    }
    //@Value("#{jobParameters['requestDate']}") String requestDate
    @Bean
    @StepScope
    public JpaPagingItemReader<PaymentResult> reader(@Value("#{jobParameters['requestDate']}") String requestDate) {

      //  String requestDate = "2023-11-08";
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("startDateTime", LocalDateTime.parse(requestDate + "T00:00:00"));
        parameters.put("endDateTime", LocalDateTime.parse(requestDate + "T23:59:59"));


       String queryString = String.format("select new %s(p.clientEmail, sum(p.totalAmount)) From Payment p "
               + "where p.approvedAt between :startDateTime and :endDateTime group by p.clientEmail", PaymentResult.class.getName());
        //String queryString = "SELECT p FROM Payment p";
        JpaPagingItemReaderBuilder<PaymentResult> jpaPagingItemReaderBuilder  = new JpaPagingItemReaderBuilder<>();
        JpaPagingItemReader<PaymentResult> paymentItemReader = jpaPagingItemReaderBuilder
                .name("paymentItemReader")
                .entityManagerFactory(entityManagerFactory) //readerEntityManagerFactory.getObject()
                .parameterValues(parameters)
                .queryString(queryString)
                .pageSize(10)
                .build();
        log.info("reader={}",paymentItemReader.toString());
        return paymentItemReader;
    }

/*    @Bean
    public QuerydslNoOffsetPagingItemReader<PaymentResult> reader1(){
        String requestDate = "2023-11-09";
        Map<String, Object> parameters = new LinkedHashMap<>();
        LocalDateTime startDateTime = LocalDateTime.parse(requestDate + "T00:00:00");
        LocalDateTime endDateTime = LocalDateTime.parse(requestDate + "T23:59:59");
        QuerydslNoOffsetNumberOptions<Payment, Long> options = new QuerydslNoOffsetNumberOptions<>(
                payment.id, Expression.ASC);

       *//* QuerydslNoOffsetPagingItemReader<Tuple> totalAmount = new QuerydslNoOffsetPagingItemReader<>(
                entityManagerFactory, CHUNK_SIZE, options,
                queryFactory -> queryFactory.select(payment.clientEmail, payment.totalAmount.sum().as("totalAmount")))
                        .from(payment)
                        .where(payment.approvedAt.between(startDateTime, endDateTime))
                        .groupBy(payment.clientEmail));
        return totalAmount;*//*
        QuerydslNoOffsetPagingItemReader<Payment> totalAmount = new QuerydslNoOffsetPagingItemReader<>(
                entityManagerFactory, CHUNK_SIZE, options,
                queryFactory -> queryFactory.select(payment.clientEmail, payment.totalAmount.sum().as("totalAmount"))
                        .from(payment)
                        .where(payment.approvedAt.between(startDateTime, endDateTime))
                        .groupBy(payment.clientEmail))
                .rowMapper(tuple -> {
                    Payment payment = new Payment();
                    payment.setClientEmail(tuple.get(payment.clientEmail));
                    payment.setTotalAmount(tuple.get(1, Long.class)); // totalAmount의 위치에 따라 수정
                    return payment;
                });

        return totalAmount;

    }*/


@Bean
public QuerydslPagingItemReader<PaymentResult> reader2(){
    String requestDate = "2023-11-09";
    Map<String, Object> parameters = new LinkedHashMap<>();
    LocalDateTime startDateTime = LocalDateTime.parse(requestDate + "T00:00:00");
    LocalDateTime endDateTime = LocalDateTime.parse(requestDate + "T23:59:59");
    //     parameters.put("startDateTime", LocalDateTime.parse(requestDate + "T00:00:00"));
    //   parameters.put("endDateTime", LocalDateTime.parse(requestDate + "T23:59:59"));
    QuerydslPagingItemReader<PaymentResult> totalAmount = new QuerydslPagingItemReader<>(entityManagerFactory,
            CHUNK_SIZE,
            queryFactory ->
                    queryFactory.select(Projections.fields(PaymentResult.class, payment.clientEmail,
                                    payment.totalAmount.sum().as("totalAmount")))
                            .from(payment)
                            .where(payment.approvedAt.between(startDateTime, endDateTime))
                            .groupBy(payment.clientEmail));
    return totalAmount;
    //Projections.fields(PaymentResult.class,payment.clientEmail,payment.totalAmount.sum().as("totalAmount"))
}





/*    @Bean
    public JpaItemWriter<PaymentResultResponseList> writer() {
        JpaItemWriter<PaymentResultResponseList> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }*/


}
