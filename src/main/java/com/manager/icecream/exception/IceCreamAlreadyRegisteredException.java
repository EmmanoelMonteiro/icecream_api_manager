package com.manager.icecream.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IceCreamAlreadyRegisteredException extends Exception{

    public IceCreamAlreadyRegisteredException(String iceCreamName) {
        super(String.format("Ice Cream with name %s already registered in the system.", iceCreamName));
    }
}
