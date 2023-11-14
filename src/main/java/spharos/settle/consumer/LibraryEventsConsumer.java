package spharos.settle.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class LibraryEventsConsumer {

  /*  @Autowired
    private SettleService settleService;*/


/*    @KafkaListener(topics = {" test-events"},containerFactory ="KafkaListenerContainerFactory")
    public void onMessage(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord : {} ", consumerRecord);

        //settleService.deserializePaymentHistory(consumerRecord);
    }*/
/*   @KafkaListener(topics = {"client-events"})
    public void clientOnMessage(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord : {} ", consumerRecord);
        //<key,<String,BankInfo>>
       // settleService.deserializeClientBankIfo(consumerRecord);
    }*/
}
