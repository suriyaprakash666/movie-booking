package com.moviebooking.movie.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    private String movieId;

    @NotBlank(message = "enter the movie title")
    private String movieTitle;

    @NotBlank(message = "enter the movie description")
    private String description;

    @NotNull(message = "enter date")
    @DateTimeFormat()
    private LocalDate releaseDate;

    @NotNull(message = "enter the duration")
    @DecimalMin(value = "80.0", message = "duration should be at least 80 minutes")
    @DecimalMax(value = "250.0", message = "duration should not be more than 250 minutes")
    private Double duration;

    @NotBlank(message = "enter the genre")
    private String genre;

    @NotBlank(message = "enter the director name")
    private String director;

    @NotBlank(message = "enter the casting")
    private String casting;

    @PrePersist
    public void onCreate()
    {
        movieId=this.releaseDate.format(DateTimeFormatter.ofPattern("yyMMM"));
        SecureRandom secureRandom = new SecureRandom();
        movieId+=secureRandom.nextInt(9000)+1000;

    }

//    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Show> shows;
}


