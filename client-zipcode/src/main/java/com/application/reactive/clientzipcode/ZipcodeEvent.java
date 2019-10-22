package com.application.reactive.clientzipcode;

import com.application.reactive.clientzipcode.Zipcode;
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
