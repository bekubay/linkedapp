package edu.mum.linkedapp;

import edu.mum.linkedapp.repository.IUserRepository;
import edu.mum.linkedapp.storage.StorageProperties;
import edu.mum.linkedapp.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = IUserRepository.class)
@EnableConfigurationProperties(StorageProperties.class)
public class LinkedappApplication {

	public static void main(String[] args) {
			SpringApplication.run(LinkedappApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}
