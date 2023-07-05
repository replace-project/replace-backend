package com.replace.re.place;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

@SpringBootApplication
public class RePlaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RePlaceApplication.class, args);
	}


	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}


	@Bean
	public ApplicationRunner runner(ApplicationContext context){
		return args -> {

		};
	}
}
