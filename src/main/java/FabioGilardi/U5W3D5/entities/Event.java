package FabioGilardi.U5W3D5.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private List<Booking> bookingList;

    public Event(String title, String description, String place, LocalDate date, int maxPartecipants) {
        this.title = title;
        this.description = description;
        this.place = place;
        this.date = date;
        this.maxPartecipants = maxPartecipants;
    }
}
