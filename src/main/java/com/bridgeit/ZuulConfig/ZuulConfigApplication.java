package com.bridgeit.ZuulConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.bridgeit.ZuulConfig.zuulfilter.PreFilter;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class ZuulConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulConfigApplication.class, args);
	}

	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}
}
