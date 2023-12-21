package org.moviebooking.show.controller;

import org.moviebooking.show.model.*;
import org.moviebooking.show.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    private final ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping("/hello")
    public String getMessage() {
        return "Hello World";
    }

    @GetMapping
    public List<Show> getAllShows() {
        return showService.getAllShows();
    }

    @GetMapping("/{showId}")
    public ResponseEntity<ShowResponseDTO> getShowById(@PathVariable String showId) {
        return showService.getShowById(showId)
                .map(show -> {
                    ShowResponseDTO responseDTO = convertToShowResponseDTO(show);
                    return ResponseEntity.ok(responseDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShowResponseDTO> createShow(@Valid @RequestBody ShowRequestDTO showRequestDTO) {
        showService.createShow(showRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{showId}")
    public ResponseEntity<ShowResponseDTO> updateShow(@PathVariable String showId,
                                                      @Valid @RequestBody Show updatedShow) {
        Show updatedShowEntity = showService.updateShow(showId, updatedShow);
        ShowResponseDTO responseDTO = convertToShowResponseDTO(updatedShowEntity);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{showId}")
    public ResponseEntity<Void> deleteShow(@PathVariable String showId) {
        showService.deleteShow(showId);
        return ResponseEntity.noContent().build();
    }

    private ShowResponseDTO convertToShowResponseDTO(Show show) {
        ShowResponseDTO responseDTO = new ShowResponseDTO();
        responseDTO.setShowId(show.getShowId());
        responseDTO.setStartTime(show.getStartTime());
        responseDTO.setEndTime(show.getEndTime());
        responseDTO.setPrice(show.getPrice());
        responseDTO.setSeatAvailability(show.getSeatAvailability());
        responseDTO.setMovieTitle(show.getMovieTitle());
        responseDTO.setCinemaName(show.getCinemaName());
        responseDTO.setScreenNo(show.getScreenNo());
        return responseDTO;
    }
}