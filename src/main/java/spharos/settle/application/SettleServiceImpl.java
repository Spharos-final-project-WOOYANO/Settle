package spharos.settle.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import spharos.settle.dto.PaymentResultResponseList;

public interface SettleServiceImpl {
    void settle(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException;
   // List<PaymentResultResponseList> processPaymentResult(ConsumerRecord<String, String> consumerRecord)throws JsonProcessingException;

}
