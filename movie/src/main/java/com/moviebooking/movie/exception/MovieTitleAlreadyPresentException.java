package com.moviebooking.movie.exception;

public class MovieTitleAlreadyPresentException extends RuntimeException{
    public MovieTitleAlreadyPresentException(String message) {
        super(message);
    }
}
