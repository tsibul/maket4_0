package ru.maket.maket4_0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;

@SpringBootApplication
public class Maket40Application {

	public static DataSource dataSource;



	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Maket40Application.class, args);
		dataSource = context.getBean(DataSource.class);

	}

}
