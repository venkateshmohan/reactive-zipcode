package com.application.reactive.clientzipcode;


import lombok.*;


@EqualsAndHashCode
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Zipcode {
      private String zipCode;

      private String city;
      private String county;
}
