package com.application.reactive.serverzipcode.model;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
public class Zipcode {
      @Field(name = "zipCode")
      private String zipCode;

      private String city;
      private String county;
}
