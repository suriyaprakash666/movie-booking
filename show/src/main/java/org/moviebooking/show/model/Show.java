package org.moviebooking.show.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalTime;
import java.util.Random;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Show {

    @Id
    String showId;

    @NotNull(message = "Start Time cannot be null")
    LocalTime startTime;

    @NotNull(message = "End Time cannot be null")
    LocalTime endTime;

    @Positive(message = "Price must be a positive value")
    double price;

    @Positive(message = "Seat Availability must be a positive value")
    int seatAvailability;

    @NotBlank(message = "Movie Title cannot be blank")
    private String movieTitle;

    @NotBlank(message = "Cinema Name cannot be blank")
    private String cinemaName;

    @Positive(message = "Screen No must be a positive value")
    private int screenNo;

    public Show() {
        generateRandomShowId();
    }

    private void generateRandomShowId() {
        StringBuilder generatedId = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            char randomChar = (char) ('A' + new Random().nextInt(26));
            generatedId.append(randomChar);
        }

        for (int i = 0; i < 6; i++) {
            generatedId.append(new Random().nextInt(10));
        }

        this.showId = generatedId.toString();
    }

}
