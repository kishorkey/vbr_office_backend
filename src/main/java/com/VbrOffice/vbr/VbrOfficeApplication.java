package com.VbrOffice.vbr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class VbrOfficeApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(VbrOfficeApplication.class, args);
	}
}
