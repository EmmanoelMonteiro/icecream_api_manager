package com.manager.icecream.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IceCreamNotFoundException extends Exception {

    public IceCreamNotFoundException(String iceCreamName) {
        super(String.format("Icre Cream with name %s not found in the system.", iceCreamName));
    }

    public IceCreamNotFoundException(Long id) {
        super(String.format("Icre Cream with id %s not found in the system.", id));
    }
}
