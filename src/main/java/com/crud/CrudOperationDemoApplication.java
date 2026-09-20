package com.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan("com.crud.entity")
public class CrudOperationDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudOperationDemoApplication.class, args);
	}

}
