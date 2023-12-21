package com.moviebooking.movie.controller;

import com.moviebooking.movie.entity.Movie;
import com.moviebooking.movie.service.IMovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {


   private final IMovieService movieService;
   @Autowired
    public MovieController(IMovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/movie")
    public ResponseEntity<Movie> createMovie(@RequestBody @Valid Movie movie){
            return movieService.createMovie(movie);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String movieId)
    {
        return movieService.getMovieById(movieId);
    }
    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAllMovies()
    {
        return movieService.getAllMovies();
    }
    @GetMapping("/movies/{releaseDate}")
    public ResponseEntity<List<Movie>> getMovieByReleaseDate(@PathVariable  @DateTimeFormat() LocalDate releaseDate)
    {
        return movieService.getMoviesByReleaseDate(releaseDate);
    }

    @GetMapping("/movies/year/{year}")
    public ResponseEntity<List<Movie>> getMovieByYear(@PathVariable  int year)
    {
        return movieService.getMoviesByYear(year);
    }
    @GetMapping("/movies/year/{year}/month/{month}")
    public ResponseEntity<List<Movie>> getMovieByYearAndMonth(@PathVariable int year,@PathVariable int month)
    {
        return movieService.getMoviesByMonthAndYear(month,year);
    }

    @GetMapping("/movies/actor/{actor}")
    public ResponseEntity<List<Movie>> getMoviesByActor(@PathVariable String actor)
    {
        return movieService.getAllMoviesOfActor(actor);
    }

    @GetMapping("/movies/genre/{genre}")
    public ResponseEntity<List<Movie>> getMoviesByGenre(@PathVariable String genre)
    {
        return movieService.getMoviesByGenre(genre);
    }
    @PutMapping("/movie/{movieId}")
    public ResponseEntity<Movie> updateMovie(@PathVariable String movieId,@RequestBody Movie movie){
       return movieService.updateMovie(movieId,movie);
    }

    @DeleteMapping("/movie/{movieId}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable String movieId){
       return movieService.deleteMovie(movieId);
    }
}
