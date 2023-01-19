package com.rebel.BlogAPIv2;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogApIv2Application {

	public static void main(String[] args) {
		SpringApplication.run(BlogApIv2Application.class, args);
	}


	@Bean
	public ModelMapper getModel()
	{
		return  new ModelMapper();
	}

}
