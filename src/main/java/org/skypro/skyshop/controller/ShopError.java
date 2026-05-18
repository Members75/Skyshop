package org.skypro.skyshop.controller;

import java.util.Objects;

public class ShopError {

    private final String code;
    private final String message;

    public ShopError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopError that = (ShopError) o;
        return code.equals(that.code) && message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message);
    }

    @Override
    public String toString() {
        return "ShopError{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}