package com.moviebooking.movie.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
@RestControllerAdvice
public class ApplicationExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleMovieNotFoundException(MovieNotFoundException ex){
        String message=ex.getMessage();
        CustomErrorResponse customErrorResponse=new CustomErrorResponse(LocalDateTime.now().toString(),404,"NOT FOUND",message);
        logger.error(message);
        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovieTitleAlreadyPresentException.class)
    public  ResponseEntity<CustomErrorResponse> handleMovieTitleAlreadyPresentException(MovieTitleAlreadyPresentException ex){
        String message=ex.getMessage();
        CustomErrorResponse customErrorResponse=new CustomErrorResponse(LocalDateTime.now().toString(),406,"Already present",message);
        logger.error(message);
        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<CustomErrorResponse> handleInvalidDateFormat(){
        String  message="Invalid date format please enter a valid date yyyy-MM-dd";
        logger.error(message);
        CustomErrorResponse customErrorResponse=new CustomErrorResponse(LocalDateTime.now().toString(),404,"bad request",message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customErrorResponse);
    }
}
