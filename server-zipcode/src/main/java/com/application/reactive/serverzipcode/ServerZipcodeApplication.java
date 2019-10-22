package com.application.reactive.serverzipcode;

import com.application.reactive.serverzipcode.model.Zipcode;
import com.application.reactive.serverzipcode.repository.ZipcodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import reactor.core.publisher.Flux;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@EnableMongoRepositories(basePackages = "com.application.reactive.serverzipcode.repository")
@ComponentScan(basePackages = "com.application.reactive.serverzipcode.resource")
@SpringBootApplication
public class ServerZipcodeApplication {
	private static final Logger log =  LoggerFactory.getLogger(Zipcode.class);
	@Bean
	CommandLineRunner commandLineRunner(ZipcodeRepository zipcodeRepository){
		  return args -> {
		  	 zipcodeRepository.deleteAll()
					 .subscribe(null,null,()->{
						 try (Stream<String> stream = Files.lines(Paths.get("C:/reactive-zipcode/Zip_Code_Lookup_Table.csv"))) {

							 stream.filter(l->!l.trim().isEmpty()).forEach(l->{
											 zipcodeRepository.save(new Zipcode(String.valueOf(l.split(",")[0]), l.split(",")[1], l.split(",")[2]))
								 .subscribe(System.out::println);
							 });

						 } catch (IOException ioe) {
							 ioe.printStackTrace();
						 }

					 });
		  };
	}
	public static void main(String[] args) {
		SpringApplication.run(ServerZipcodeApplication.class, args);
	}

}
