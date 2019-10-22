package com.application.reactive.clientzipcode;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ClientZipcodeApplication {
	@Bean
	WebClient webClient(){
		return WebClient.create("http://localhost:9000/rest/zipcode");
	}
	@Bean
	CommandLineRunner commandLineRunner(WebClient webClient){
		Scanner scanner = new Scanner(System.in).useDelimiter("\n");
		System.out.println("Receive streams from particular city or county?");
		String command= scanner.next();
		  return strings->{
			  Flux<Zipcode> zipcodeFlux= webClient.get()
					  .uri("/all")
					  .retrieve()
					  .bodyToFlux(Zipcode.class);
			   switch(command)
			   { case "County":
			   {
				   String countyCommand= scanner.next();
				   zipcodeFlux.filter(zipcode -> {
				   	return zipcode.getCounty().equals(countyCommand);})
						   .flatMap(zipcode -> {
						   	return webClient.get()
								   .uri("/county/{cnty}/events",zipcode.getCounty())
								   .retrieve()
								   .bodyToFlux(ZipcodeEvent.class);
						     })
						   .subscribe(System.out::println);
			   }
			   break;
			   	case "city":
			   {String cityCommand= scanner.next();
					  zipcodeFlux.filter(zipcode -> zipcode.getCity().equals(cityCommand))
					  .flatMap(zipcode -> webClient.get()
					      .uri("/city/{cty}/events",zipcode.getCity())
					      .retrieve()
					      .bodyToFlux(ZipcodeEvent.class))
					  .subscribe(System.out::println);
					  };
					  break;
			  }
		  };
		  }

	public static void main(String[] args) {
		SpringApplication.run(ClientZipcodeApplication.class, args);
	}

}
