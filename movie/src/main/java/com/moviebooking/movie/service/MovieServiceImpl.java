package com.moviebooking.movie.service;

import com.moviebooking.movie.entity.Movie;
import com.moviebooking.movie.exception.MovieNotFoundException;
import com.moviebooking.movie.exception.MovieTitleAlreadyPresentException;
import com.moviebooking.movie.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovieServiceImpl implements IMovieService{

    private final MovieRepository movieRepository;
    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);
    private String message;


    @Override
    public ResponseEntity<Movie> createMovie(Movie movie) {
        if(movieRepository.existsByMovieTitle(movie.getMovieTitle()))
        {
            throw new MovieTitleAlreadyPresentException("movie title already present");
        }
        logger.info("uploading a new movie..");
        return ResponseEntity.ok(movieRepository.save(movie));
    }

    @Override
    public ResponseEntity<Movie> getMovieById(String movieId) {
        Movie movie=movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("Movie with this id not present, check it"));
        message="Found a movie with id: "+movieId;
        logger.info(message);
        return ResponseEntity.ok(movie);
    }

    @Override
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies=movieRepository.findAll();
        if(movies.isEmpty()) {
            throw new MovieNotFoundException("Sorry no movies found");
        }
        logger.info("found all movies");
        return ResponseEntity.ok(movies);
    }

    @Override
    public ResponseEntity<List<Movie>> getAllMoviesOfActor(String actor) {
        List<Movie> movies=getAllMovies().getBody();
        List<Movie> movieList= null;
        if (movies != null) {
            movieList = movies.stream()
                                 .filter(movie -> movie
                                         .getCasting()
                                          .contains(actor)).toList();
        }
        if(movieList==null||movieList.isEmpty()){
            throw new MovieNotFoundException("sorry no movie found with actor "+actor);
        }
        message="found all movies of actor:"+actor;
        logger.info(message);
        return ResponseEntity.ok(movieList);
    }

    @Override
    public ResponseEntity<List<Movie>> getMoviesByGenre(String genre) {
        List<Movie> movies=movieRepository.findByGenre(genre);
        if(movies.isEmpty())
        {
            throw new MovieNotFoundException("no movies found in this genre");
        }
        message="found all movies on genre";
        logger.info(message);
        return ResponseEntity.ok(movies);

    }

    @Override
    public ResponseEntity<Movie> updateMovie(String movieId, Movie movie) {
        Movie existingMovie=movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("Please enter valid movie id"));
        movie.setMovieId(existingMovie.getMovieId());
        movieRepository.save(movie);
        message="updated movie with id:"+movieId;
        logger.info(message);
        return ResponseEntity.ok(movie);
    }
    @Override
    public ResponseEntity<List<Movie>> getMoviesByReleaseDate(LocalDate releaseDate) {
        List<Movie> movies=movieRepository.findByReleaseDate(releaseDate);
        if (movies.isEmpty())
        {
            throw new MovieNotFoundException("No movies released in this date:"+releaseDate);
        }
        message="found all movies by release date:"+releaseDate;
        logger.info(message);
        return ResponseEntity.ok(movies);
    }

    @Override
    public ResponseEntity<List<Movie>> getMoviesByYear(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        List<Movie> movies= movieRepository.findByReleaseDateBetween(startDate, endDate);
        if(movies.isEmpty())
        {
            throw new MovieNotFoundException("no movies released in this year:"+year);
        }
        message="found movies in the year:"+year;
        logger.info(message);
        return ResponseEntity.ok(movies);
    }

    @Override
    public ResponseEntity<List<Movie>> getMoviesByMonthAndYear(int month, int year) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = LocalDate.of(year, month, startDate.lengthOfMonth());
        List<Movie>movies= movieRepository.findByReleaseDateBetween(startDate, endDate);
        if(movies.isEmpty())
        {
            throw new MovieNotFoundException("no movies released in this year:"+year+"month:"+month);
        }
        message="found movies released in the year:"+year+" month:"+month;
        logger.info(message);
        return ResponseEntity.ok(movies);
    }

    @Override
    public ResponseEntity<Movie> deleteMovie(String movieId) {
        Movie movie=movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("Movie with this id not present, check it"));
        movieRepository.delete(movie);
        message="deleted movie with movieId:"+movieId;
        logger.info(message) ;
        return ResponseEntity.ok(movie);
    }
}
