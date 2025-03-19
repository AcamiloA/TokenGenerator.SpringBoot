package com.andresalarcon.tokengenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = "com.andresalarcon.tokengenerator")
@PropertySource("classpath:application.yml")
public class TokengeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokengeneratorApplication.class, args);
	}

}
