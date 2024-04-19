package FabioGilardi.U5W3D5.repositories;

import FabioGilardi.U5W3D5.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EventDAO extends JpaRepository<Event, Long> {
    boolean existsByPlaceAndDate(String place, LocalDate date);
}
