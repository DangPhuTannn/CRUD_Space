package com.example.CRUDSpace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.CRUDSpace.Configuration.QenergyProperties;
import com.example.CRUDSpace.Configuration.TuyaProperties;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({ TuyaProperties.class, QenergyProperties.class })
public class CrudSpaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpaceApplication.class, args);
	}

}
