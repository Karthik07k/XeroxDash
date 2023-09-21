package com.college.GetTheCopy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.college.GetTheCopy.common.dto.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageProperties.class })
@EnableJpaAuditing
public class GetTheCopyApplication {
	public static void main(String[] args) {
		SpringApplication.run(GetTheCopyApplication.class, args);
	}
}
