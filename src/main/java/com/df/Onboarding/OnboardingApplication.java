package com.df.Onboarding;

import org.springframework.boot.SpringApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.df.Onboarding.users.controller.OnboarderController.log;


@SpringBootApplication
public class OnboardingApplication {


	public static void main(String[] args) {
		SpringApplication.run(OnboardingApplication.class, args);
		log.info("Vaishnavi made changes at: "+ System.currentTimeMillis() );
	}



}
