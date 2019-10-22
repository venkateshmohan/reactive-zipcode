package com.application.reactive.serverzipcode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ZipcodeEvent {
    private Zipcode zipcode;
    private Date date;
}
