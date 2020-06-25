package com.gzoul.springbootapachecamel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootApacheCamelApplication extends RouteBuilder {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApacheCamelApplication.class, args);
	}

	@Override
	public void configure() throws Exception {
		System.out.println("Started");
		from("file:C:/Users/Ghufr/OneDrive/Desktop/source1?noop=true").to("file:C:/Users/Ghufr/OneDrive/Desktop/target1");
		System.out.println("Ended");
	}
}
