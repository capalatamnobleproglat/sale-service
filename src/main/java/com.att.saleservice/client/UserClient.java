package com.att.saleservice.client;

import com.att.saleservice.dto.UserDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



public interface UserClient {

    @GetMapping("/users/{id}")
    UserDto getUser(@PathVariable("id") Long id);
}