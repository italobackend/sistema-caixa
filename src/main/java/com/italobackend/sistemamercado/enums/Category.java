package com.italobackend.sistemamercado.enums;

public enum Category {
    FOOD("Comida"),
    DRINK("Bebidas");


    private final String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
