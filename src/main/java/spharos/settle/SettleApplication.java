package spharos.settle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SettleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SettleApplication.class, args);
	}

}
