/*
package spharos.settle.consumer;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
//@EnableKafka
@Slf4j
public class LibraryEventsConsumerConfig {

    @Autowired
    KafkaProperties kafkaProperties;


    public DeadLetterPublishingRecoverer publishingRecoverer() {

        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate
                , (r, e) -> {
            log.error("Exception in publishingRecoverer : {} ", e.getMessage(), e);
            if (e.getCause() instanceof RecoverableDataAccessException) {
                return new TopicPartition(retryTopic, r.partition());
            } else {
                return new TopicPartition(deadLetterTopic, r.partition());
            }
        }
        );

        return recoverer;

    }


    public DefaultErrorHandler errorHandler() {

        //재시도 하고싶지않은 예외
        var exceptiopnToIgnorelist = List.of(
                IllegalArgumentException.class
        );

        // fixedback처럼 재시도 수행하는건데 fix는 1초마다 고정 반복이고 이거는 점차 늘어남 2개중 아무거나 사용
        //최대 재시도 횟수가 2번으로 설정된
        ExponentialBackOffWithMaxRetries expBackOff = new ExponentialBackOffWithMaxRetries(2);
        expBackOff.setInitialInterval(1_000L); // 첫번쨰 1초 첫 번째 재시도 이후에 이 시간이 두 배로 증가하게 됩니다.
        expBackOff.setMultiplier(2.0);
        expBackOff.setMaxInterval(2_000L); //시도 간격의 최대 2초로 제한합니다. 이 값은 재시도 간격이 더 이상 증가하지 않는 최대 값입니다.

        //고정된 시간 간격으로 재시도를 수행하는 백오프 전략을 제공합니다.
        //1000L: 1000 밀리초 또는 1초 동안 대기합니다. 첫 번째 재시도 후에 1초를 대기하고 다시 시도합니다.
        //2L: 최대 재시도 횟수입니다. 즉, 실패한 작업을 최대 2번까지 재시도할 수 있습니다.
        var fixedBackOff = new FixedBackOff(1000L, 2L);

        */
/**
         * Just the Custom Error Handler
         *//*

        // var defaultErrorHandler =  new DefaultErrorHandler(fixedBackOff);

        */
/**
         * Error Handler with the BackOff, Exceptions to Ignore, RetryListener
         *//*


        var defaultErrorHandler = new DefaultErrorHandler(
                //consumerRecordRecoverer
                publishingRecoverer()
                ,
                fixedBackOff
                //expBackOff
        );

        exceptiopnToIgnorelist.forEach(defaultErrorHandler::addNotRetryableExceptions);

        defaultErrorHandler.setRetryListeners(
                (record, ex, deliveryAttempt) ->
                        log.info("Failed Record in Retry Listener  exception : {} , deliveryAttempt : {} ", ex.getMessage(), deliveryAttempt)
        );

        return defaultErrorHandler;
    }

    @Bean
    @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
    ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ObjectProvider<ConsumerFactory<Object, Object>> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory
                .getIfAvailable(() -> new DefaultKafkaConsumerFactory<>(this.kafkaProperties.buildConsumerProperties())));
        factory.setConcurrency(3);
        factory.setCommonErrorHandler(errorHandler());  //오류처리
        return factory;
    }
}
*/
