package com.moviebooking.movie.service;

import com.moviebooking.movie.entity.Movie;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface IMovieService {

    ResponseEntity<Movie> createMovie(Movie movie);
    ResponseEntity<Movie> getMovieById(String movieId);
    ResponseEntity<List<Movie>> getAllMovies();
    ResponseEntity<List<Movie>> getAllMoviesOfActor(String actor);
    ResponseEntity<List<Movie>> getMoviesByGenre(String genre);
    ResponseEntity<List<Movie>> getMoviesByReleaseDate(LocalDate releaseDate);
    ResponseEntity<List<Movie>> getMoviesByYear(int year);
    ResponseEntity<List<Movie>> getMoviesByMonthAndYear(int month,int year);
    ResponseEntity<Movie> updateMovie(String movieId, Movie movie);
    ResponseEntity<Movie> deleteMovie(String movieId);
}
