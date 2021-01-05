package com.mercadolibre.quasar_fire_operation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.mercadolibre.quasar_fire_operation"})
public class QuasarFireOperationApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuasarFireOperationApplication.class, args);
	}

}
