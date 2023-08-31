package kz.almanit.orderinfobot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableFeignClients(basePackages = {"kz.almanit.orderinfobot"})
public class OrderInfoBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderInfoBotApplication.class, args);
	}

}
