package com.att.saleservice.client;

import com.att.saleservice.dto.CarDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


public interface CarClient {


    @GetMapping("/cars/{id}")
    CarDto getCar(@PathVariable("id") Long id);
}
