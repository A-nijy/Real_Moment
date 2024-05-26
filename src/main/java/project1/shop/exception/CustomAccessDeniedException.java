package project1.shop.exception;

import org.springframework.stereotype.Component;


public class CustomAccessDeniedException extends RuntimeException {

    public CustomAccessDeniedException(String message) {
        super(message);
    }
}
