package org.moviebooking.screen.service;

import org.moviebooking.screen.model.Screen;
import org.moviebooking.screen.repository.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class ScreenService {
    private final ScreenRepository screenRepository;
    private final RestTemplate restTemplate;

    private static final String CINEMA_SERVICE_URL = "http://cinema-service/api/cinemas/";

    @Autowired
    public ScreenService(ScreenRepository screenRepository, RestTemplate restTemplate) {
        this.screenRepository = screenRepository;
        this.restTemplate = restTemplate;
    }

    public List<Screen> getAllScreens() {
        return screenRepository.findAll();
    }

    public Screen getScreenById(int screenNo) {
        return screenRepository.findById(screenNo).orElse(null);
    }

    public Screen saveScreen(Screen screen) {
        String cinemaId = screen.getCinemaName();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(CINEMA_SERVICE_URL + cinemaId, String.class);
        String cinemaName = responseEntity.getBody();

        // Assuming cinemaId is used as cinemaName for simplicity
        screen.setCinemaName(cinemaName);

        return screenRepository.save(screen);
    }

    public void deleteScreen(int screenNo) {
        screenRepository.deleteById(screenNo);
    }
}
