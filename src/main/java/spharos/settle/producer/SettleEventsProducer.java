/*
package spharos.settle.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
@Slf4j
public class SettleEventsProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    @Value("${spring.kafka.topic}")
    public String topic;


    public CompletableFuture<SendResult<String, String>> sendLibraryEvent(List<String> list)
            throws JsonProcessingException {
        log.info("Sending Monthly Payment Event");
        var key = "settle-test";
        var value = objectMapper.writeValueAsString(list);
        //send하면 2개
        //1.blocking call- kafka cluster에 대한 메타데이터를 가져온다 - 이게실패하면 메세지 못보냄
        //2.메세지 보내기가 실제로 발생하고 비동기 반환 send message happens - return a completableFuture
        log.info("value : {}", value);
        var completableFuture = kafkaTemplate.send(topic, key, value);
        return completableFuture
                .whenComplete((sendResult, throwable) -> {
                    if (throwable != null) {
                        handleFailure(key, value, throwable);
                    } else {
                        handleSuccess(key, value, sendResult);

                    }
                });

    }
    private void handleFailure(String key, String value, Throwable ex) {
        log.error("Error Sending the Message and the exception is {}", ex.getMessage());
//        try {
//            throw ex;
//        } catch (Throwable throwable) {
//            log.error("Error in OnFailure: {}", throwable.getMessage());
//        }

    }
    private void handleSuccess(String key, String value, SendResult<String, String> result) {
        log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}",
                key, value, result.getRecordMetadata().partition());

    }
}
*/
