package com.rebel.BlogAPIv2;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogApIv2Application implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(BlogApIv2Application.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public ModelMapper getModel()
	{
		return  new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception
	{
	 String encoded = passwordEncoder.encode("1234");
		System.out.println(encoded);
	}
}
