package edu.mum.linkedapp;

import edu.mum.linkedapp.repository.IUserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = IUserRepository.class)
public class LinkedappApplication {

	public static void main(String[] args) {
			SpringApplication.run(LinkedappApplication.class, args);
	}

}
