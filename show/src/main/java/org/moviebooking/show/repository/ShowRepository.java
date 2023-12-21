package org.moviebooking.show.repository;

import org.moviebooking.show.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, String> {
}
