package org.moviebooking.show.model;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ShowResponseDTO {
    private String showId;
    private LocalTime startTime;
    private LocalTime endTime;
    private double price;
    private int seatAvailability;
    private String movieTitle;
    private String cinemaName;
    private int screenNo;
}
