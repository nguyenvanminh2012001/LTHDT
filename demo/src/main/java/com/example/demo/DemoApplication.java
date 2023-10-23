package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@Slf4j
public class DemoApplication {
	@Value(value = "${app.timezone}")
	String appTimeZone;

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone(appTimeZone));
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("App started");
	}

}
