package com.oneirros.sally2.exception;

public class SamePasswordException extends Exception {

    public SamePasswordException() {
    }

    public SamePasswordException(String message) {
        super(message);
    }
}
