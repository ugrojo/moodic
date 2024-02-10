package com.moodic.moodic;

import com.moodic.config.SpringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = {SpringConfig.class, APIController.class})
@SpringBootApplication
public class MoodicApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoodicApplication.class, args);
	}
}
