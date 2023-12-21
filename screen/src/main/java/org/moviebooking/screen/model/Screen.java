package org.moviebooking.screen.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Screen {
    @Id
    private int screenNo;
    private String cinemaName;

}
