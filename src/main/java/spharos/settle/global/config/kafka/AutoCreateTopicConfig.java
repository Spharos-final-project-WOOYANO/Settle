package spharos.settle.global.config.kafka;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

//@Configuration
//@Profile("local")
public class AutoCreateTopicConfig {

    @Value("${spring.kafka.topic}")
    public String topic;

    //토픽생성
    @Bean
    public NewTopic libraryEvents(){
        return TopicBuilder.name(topic)
                .partitions(3)
                .replicas(3)
                .build();
    }

}