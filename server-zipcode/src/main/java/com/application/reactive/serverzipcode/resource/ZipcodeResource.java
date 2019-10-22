package com.application.reactive.serverzipcode.resource;

import com.application.reactive.serverzipcode.model.Zipcode;
import com.application.reactive.serverzipcode.model.ZipcodeEvent;
import com.application.reactive.serverzipcode.repository.ZipcodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

@RestController
@RequestMapping("/rest/zipcode")
public class ZipcodeResource {
     private ZipcodeRepository zipcodeRepository;
    public ZipcodeResource(ZipcodeRepository zipcodeRepository) {
        this.zipcodeRepository = zipcodeRepository;
    }
    @GetMapping
    public String hello(){
        return "Hello world";
    }
    @GetMapping(value = "/all")
     public Flux<Zipcode> getAll(){return zipcodeRepository.findAll();}

    @GetMapping(value = "/code/{zipcode}")
    public Mono<Zipcode> getId(@PathVariable("zipcode") final String zipcode)
    { return zipcodeRepository.findByZipCode(zipcode);
    }
    @PostMapping
    public Mono<Zipcode> addZipcode(@RequestBody Zipcode zipcode)
    {
        return zipcodeRepository.save(zipcode);
    }
    @GetMapping(value = "/county/{cnty}")
    public Flux<Zipcode> getCounty(@PathVariable("cnty") final String county)
    {
        return zipcodeRepository.findByCounty(county);
    }
    @GetMapping(value = "/city/{cty}")
    public Flux<Zipcode> getCity(@PathVariable("cty") final String city)
    {
        return zipcodeRepository.findByCity(city);
    }
    @DeleteMapping(value = "/code/{zipcode}")
    public Mono<ResponseEntity<Void>> deleteZipCode(@PathVariable("zipcode") final String zipcode)
    {

        return zipcodeRepository.findByZipCode(zipcode)
                .flatMap(existingCode ->
                        zipcodeRepository.delete(existingCode)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping(value = "/county/{cnty}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ZipcodeEvent> getCountyEvents(@PathVariable("cnty") final String county)
    {
        return zipcodeRepository.findByCounty(county)
                .flatMap(zipcode -> getFlux(zipcode));
    }
    @GetMapping(value = "/city/{cty}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ZipcodeEvent> getCityEvents(@PathVariable("cty") final String city)
    {
        return zipcodeRepository.findByCity(city)
                .flatMap(zipcode -> getFlux(zipcode));
    }
    public Flux<ZipcodeEvent> getFlux(Zipcode zipcode)
    {
        Flux<Long> interval= Flux.interval(Duration.ofSeconds(2));
        Flux<ZipcodeEvent> zipcodeFlux=
                Flux.fromStream(
                        Stream.generate(()-> new ZipcodeEvent(zipcode,new Date()))
                );
        return Flux.zip(interval,zipcodeFlux)
                .map(Tuple2::getT2);
    }
}
