package com.manager.icecream.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IceCreamType {

    INDUSTRIAL("Industrial"),
    HANDMADE("Handmade"),
    ITALIAN("Italian"),
    PREMIUM("Premium");

    private final String description;
}
