package org.skypro.skyshop.controller;

public final class ShopError {

    private String code;
    private String message;

    public ShopError() {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public String toString() {
        return "ShopError{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}