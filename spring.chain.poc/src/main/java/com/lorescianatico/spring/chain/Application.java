package com.lorescianatico.spring.chain;

import com.lorescianatico.chain.ChainOfResponsibilityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * The application main class
 */
@SpringBootApplication
@Import(ChainOfResponsibilityConfig.class)
public class Application {

	/**
	 * Main method
	 * @param args launch arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
