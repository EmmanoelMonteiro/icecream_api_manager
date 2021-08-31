package com.manager.icecream.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IceCreamExceededException extends Exception {

    public IceCreamExceededException(Long id, int quantityToIncrement) {
        super(String.format("Ice Cream with %s ID to increment informed exceeds the max manager capacity: %s", id, quantityToIncrement));
    }
}
