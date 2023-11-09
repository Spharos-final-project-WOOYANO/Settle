package spharos.settle.batch;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.Tuple;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import spharos.settle.batch.processor.PaymentItemProcessor;
import spharos.settle.batch.writer.SettleItemWriter;
import spharos.settle.domain.payment.Payment;
import spharos.settle.domain.payment.PaymentStatus;
import spharos.settle.dto.PaymentResult;
import spharos.settle.dto.PaymentResultResponseList;
import spharos.settle.dto.QPaymentResultResponseList;

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

    private final PaymentStatus doneStatus = PaymentStatus.DONE;
    private final PaymentStatus cancelStatus = PaymentStatus.CANCEL;

    @Bean
    public Job createJob() {
        return new JobBuilder("job21", jobRepository)
                .start(settleStep())
                .build();
    }

    @Bean
    public Step settleStep() {
        log.info("fileStep");
        return new StepBuilder("fileStep", jobRepository)
                .<PaymentResult,PaymentResult>chunk(10,transactionManager) // Chunk 크기를 지정
                .reader(reader())
               // .processor(paymentItemProcessor)
                .writer(settleItemWriter)
           //     .transactionManager(BeanUtils.getTransactionManagerBean(2))
                .build();

    }
    //@Value("#{jobParameters['requestDate']}") String requestDate
    @Bean
  //  @StepScope
    public JpaPagingItemReader<PaymentResult> reader() {

        String requestDate = "2023-11-08";
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("startDateTime", LocalDateTime.parse(requestDate + "T00:00:00"));
        parameters.put("endDateTime", LocalDateTime.parse(requestDate + "T23:59:59"));


       String queryString = String.format("select new %s(p.clientEmail, p.totalAmount) From Payment p where p.approvedAt between :startDateTime and :endDateTime group by p.clientEmail", PaymentResult.class.getName());
        //String queryString = "SELECT p FROM Payment p";
        JpaPagingItemReaderBuilder<PaymentResult> jpaPagingItemReaderBuilder  = new JpaPagingItemReaderBuilder<>();
        JpaPagingItemReader<PaymentResult> paymentItemReader = jpaPagingItemReaderBuilder
                .name("paymentItemReader")
                .entityManagerFactory(readerEntityManagerFactory.getObject())
                .parameterValues(parameters)
                .queryString(queryString)
                .pageSize(10)
                .build();
        log.info("reader={}",paymentItemReader);
        return paymentItemReader;
    }


    @Bean
    public JpaItemWriter<PaymentResultResponseList> writer() {
        JpaItemWriter<PaymentResultResponseList> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

}
