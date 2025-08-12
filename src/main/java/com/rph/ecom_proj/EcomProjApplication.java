package com.rph.ecom_proj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

@SpringBootApplication
public class EcomProjApplication {

	public static void main(String[] args) {

		SpringApplication.run(EcomProjApplication.class, args); //Creates the IOC(Inversion Of Control) container in JVM.

		//These all properties and libraries are packed inside a folder named as dependency.
		//Dependencies can be added in the file of pom.xml which is the part of Maven.
		//Maven is basically the tool to deal with the dependencies.
		//Dependencies can be referenced and downloaded from the web by inserting them in pom.xml through the website called as Maven repository.

	}
}

