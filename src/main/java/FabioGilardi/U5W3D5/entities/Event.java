package FabioGilardi.U5W3D5.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    private String title, description, place;

    private LocalDate date;

    private int maxPartecipants;

    public Event(String title, String description, String place, LocalDate date, int maxPartecipants) {
        this.title = title;
        this.description = description;
        this.place = place;
        this.date = date;
        this.maxPartecipants = maxPartecipants;
    }
}
