package org.moviebooking.screen.controller;

import org.moviebooking.screen.model.Screen;
import org.moviebooking.screen.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/screens")
public class ScreenController {

    private final ScreenService screenService;

    @Autowired
    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @GetMapping
    public List<Screen> getAllScreens() {
        return screenService.getAllScreens();
    }

    @GetMapping("/{screenNo}")
    public ResponseEntity<Screen> getScreenById(@PathVariable int screenNo) {
        Screen screen = screenService.getScreenById(screenNo);
        return ResponseEntity.ok(screen);
    }

    @PostMapping
    public ResponseEntity<Screen> createScreen(@Valid @RequestBody Screen screen) {
        Screen savedScreen = screenService.saveScreen(screen);
        return new ResponseEntity<>(savedScreen, HttpStatus.CREATED);
    }

    @DeleteMapping("/{screenNo}")
    public ResponseEntity<Void> deleteScreen(@PathVariable int screenNo) {
        screenService.deleteScreen(screenNo);
        return ResponseEntity.noContent().build();
    }
}
