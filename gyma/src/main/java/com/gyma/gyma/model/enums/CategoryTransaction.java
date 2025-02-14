package com.gyma.gyma.model.enums;

import lombok.Getter;

@Getter
public enum CategoryTransaction {

    MEMBERSHIP("Mensalidade"),
    SALARIES("Salário"),
    EQUIPAMENT("Equipamento"),
    OTHERS("Outros");

    private final String description;

    CategoryTransaction(String description) {
        this.description = description;
    }
}
