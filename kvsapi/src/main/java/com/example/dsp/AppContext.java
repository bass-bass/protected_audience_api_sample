package com.example.dsp;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.example.dsp.common.logger.Logger;

@SpringBootApplication
@EnableAutoConfiguration
public class AppContext extends SpringBootServletInitializer {

	private static final Logger logger = Logger.getLogger(AppContext.class.getSimpleName());

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AppContext.class);
	}

	@PostConstruct
	public void init() {
		try {
			logger.info("init load start");

			logger.info("init load end");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("init error:", e);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(AppContext.class, args);
	}

}
