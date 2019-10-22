package com.application.reactive.serverzipcode.repository;

import com.application.reactive.serverzipcode.model.Zipcode;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ZipcodeRepository extends ReactiveMongoRepository<Zipcode,String> {
     @Query(value = " {'county' : ?0} ")
     Flux<Zipcode> findByCounty(String county);
    @Query(value = " {'zipCode' : ?0 } ")
    Mono<Zipcode> findByZipCode(String zipCode);
    @Query(value = "{ 'city' : ?0 } ")
    Flux<Zipcode> findByCity(String city);
}
