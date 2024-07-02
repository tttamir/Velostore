package com.velostore.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    BICYCLE("Велосипеды"),
    SUPPLIES("Велотовары"),
    ;
    private final String name;
}
