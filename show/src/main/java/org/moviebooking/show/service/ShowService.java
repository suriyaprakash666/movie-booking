package org.moviebooking.show.service;

import org.moviebooking.show.exception.ShowException;
import org.moviebooking.show.model.*;
import org.moviebooking.show.repository.ShowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;

@Service
public class ShowService {

    private static final Logger logger = LoggerFactory.getLogger(ShowService.class);

    private final ShowRepository showRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public ShowService(ShowRepository showRepository, RestTemplate restTemplate) {
        this.showRepository = showRepository;
        this.restTemplate = restTemplate;
    }

    public List<Show> getAllShows() {
        logger.info("Fetching all shows");
        return showRepository.findAll();
    }

    public Optional<Show> getShowById(String showId) {
        logger.info("Fetching show by ID: {}", showId);
        return showRepository.findById(showId);
    }

    public Show saveShow(Show show) {
        logger.info("Saving show: {}", show);
        try {
            return showRepository.save(show);
        } catch (Exception e) {
            logger.error("Error saving show: {}", e.getMessage());
            throw new ShowException("Error saving show");
        }
    }

    public Show updateShow(String showId, Show updatedShow) {
        logger.info("Updating show with ID: {}", showId);
        Show existingShow = showRepository.findById(showId)
                .orElseThrow(() -> new ShowException("Show not found with ID: " + showId));

        updateIfNotNull(existingShow::setStartTime, updatedShow.getStartTime());
        updateIfNotNull(existingShow::setEndTime, updatedShow.getEndTime());
        updateIfPositive(existingShow::setPrice, updatedShow.getPrice());
        updateIfPositive(existingShow::setSeatAvailability, updatedShow.getSeatAvailability());
        updateIfNotNullString(existingShow::setMovieTitle, updatedShow.getMovieTitle());
        updateIfNotNullString(existingShow::setCinemaName, updatedShow.getCinemaName());
        updateIfPositive(existingShow::setScreenNo, updatedShow.getScreenNo());

        return showRepository.save(existingShow);
    }

    private void updateIfNotNull(Consumer<LocalTime> setter, LocalTime value) {
        if (value != null) {
            setter.accept(value);
        }
    }

    private void updateIfNotNullString(Consumer<String> setter, String value) {
        if (value != null) {
            setter.accept(value);
        }
    }

    private void updateIfPositive(IntConsumer setter, int value) {
        if (value > 0) {
            setter.accept(value);
        }
    }

    private void updateIfPositive(DoubleConsumer setter, double value) {
        if (value > 0) {
            setter.accept(value);
        }
    }

    public void deleteShow(String showId) {
        logger.info("Deleting show by ID: {}", showId);
        try {
            showRepository.deleteById(showId);
        } catch (Exception e) {
            logger.error("Error deleting show: {}", e.getMessage());
            throw new ShowException("Error deleting show with ID: " + showId);
        }
    }

    public void createShow(ShowRequestDTO showRequestDTO) {
        ScreenDetailsDTO screenDetails = fetchScreenDetails(showRequestDTO.getScreenNo());
        MovieDetailsDTO movieDetails = fetchMovieDetails(showRequestDTO.getMovieId());

        if (screenDetails != null && movieDetails != null) {
            Show show = Show.builder()
                    .startTime(LocalTime.now())
                    .endTime(LocalTime.now().plusHours(2))
                    .price(10.0)
                    .seatAvailability(100)
                    .movieTitle(movieDetails.getMovieTitle())
                    .cinemaName(screenDetails.getCinemaName())
                    .screenNo(screenDetails.getScreenNo())
                    .build();

            saveShow(show);
        } else {
            logger.error("Failed to retrieve movie or screen details for show creation.");
            throw new ShowException("Failed to retrieve movie or screen details.");
        }
    }

    private MovieDetailsDTO fetchMovieDetails(String movieId) {
        String movieServiceUrl = "http://localhost:8081/api/movies/{movieId}";
        return restTemplate.getForObject(movieServiceUrl, MovieDetailsDTO.class, movieId);
    }

    private ScreenDetailsDTO fetchScreenDetails(int screenNo) {
        String screenServiceUrl = "http://localhost:8082/api/screens/{screenNo}";
        return restTemplate.getForObject(screenServiceUrl, ScreenDetailsDTO.class, screenNo);
    }
}
