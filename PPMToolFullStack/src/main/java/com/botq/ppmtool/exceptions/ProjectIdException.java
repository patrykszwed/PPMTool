package com.botq.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // every time we throw this exception, we give the user a HttpStatus code for BAD_REQUEST
public class ProjectIdException extends RuntimeException{

    public ProjectIdException(String s) {
        super(s);
    }
}
