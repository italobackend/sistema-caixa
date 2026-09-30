package com.italobackend.sistemamercado.enums;

public enum Role {
    ADMIN("Administrador"),
    USER("Funcionário");

    private final String description;

    Role(String descricao) {
        this.description = descricao;
    }

    public String getDescription() {
        return description;
    }
}
