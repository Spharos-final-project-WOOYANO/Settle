package spharos.settle.consumer;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;
import spharos.settle.dto.PaymentResult;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@Slf4j
@EnableConfigurationProperties(KafkaProperties.class)
public class ConsumerConfiguration {
    @Autowired
    KafkaProperties properties;

    @Autowired
    KafkaTemplate kafkaTemplate;

    /*public ConsumerConfiguration(KafkaProperties properties) {
        this.properties = properties;
    }*/

    @Value("${topics.retry:library-events.RETRY}")
    private String retryTopic;

    @Value("${topics.dlt:library-events.DLT}")
    private String deadLetterTopic;

    @Bean
    public Map<String, Object> stringConsumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "settle");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "500");
        return props;
    }

    @Bean
    public ConsumerFactory<String, String> stringConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(stringConsumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringConsumerFactory());
        factory.setBatchListener(true);
        //  factory.setConcurrency(3);
    //    factory.setCommonErrorHandler(errorHandler());
        return factory;
    }

/*    private DefaultErrorHandler errorHandler() {
        var fixedBackOff = new FixedBackOff(1000L, 2L);
        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler(
                publishingRecover(),
                fixedBackOff
        );
        defaultErrorHandler.setRetryListeners(
                (record, ex, deliveryAttempt) ->
                        log.info("Failed Record in Retry Listener  exception : {} , deliveryAttempt : {} ", ex.getMessage(), deliveryAttempt)
        );  //메서드를 사용하여 재시도 중에 발생한 이벤트를 리스닝하는 리스너를 설정
        return defaultErrorHandler;
    }

    private DeadLetterPublishingRecoverer publishingRecover() {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate
                , (r, e) -> {
          //  log.error("Exception in publishingRecoverer : {} ", e.getMessage(), e);
            if (e.getCause() instanceof RecoverableDataAccessException) {
                return new TopicPartition(retryTopic, r.partition());
            } else {
                return new TopicPartition(deadLetterTopic, r.partition());
            }
        }
        );

        return recoverer;
    }*/

    @Bean
    public Map<String, Object> DTOConsumerConfigs() {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
     //   props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
     //   props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "spharos.settle.dto.PaymentResult");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "settle");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "500");
        return props;
    }

    @Bean
    public ConsumerFactory<String, PaymentResult> DTOConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(DTOConsumerConfigs(),  new StringDeserializer(),
                new JsonDeserializer<>(PaymentResult.class,false));
    }

    @Bean("DTOKafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, PaymentResult>> DTOKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentResult> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(DTOConsumerFactory());
      //  factory.setBatchListener(true);
        return factory;
    }
}
