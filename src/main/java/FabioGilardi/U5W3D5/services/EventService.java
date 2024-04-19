package FabioGilardi.U5W3D5.services;

import FabioGilardi.U5W3D5.entities.Event;
import FabioGilardi.U5W3D5.exceptions.BadRequestException;
import FabioGilardi.U5W3D5.exceptions.NotFoundException;
import FabioGilardi.U5W3D5.payloads.NewEventDTO;
import FabioGilardi.U5W3D5.payloads.UpdateEventDTO;
import FabioGilardi.U5W3D5.repositories.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventDAO eventDAO;

    public Page<Event> findAll(int number, int size, String sortBY) {
        Pageable pageable = PageRequest.of(number, size, Sort.by(sortBY));
        return this.eventDAO.findAll(pageable);
    }

    public Event findById(long id) {
        return this.eventDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id) {
        Event found = this.findById(id);
        this.eventDAO.delete(found);
    }

    //    INSERISCO UN CONTROLLO PER FAR SI CHE NON CI SIANO DUE EVENTI NELLO STESSO LUOGO E NELLO STESSO GIORNO
    public Event save(NewEventDTO payload) {
        if (!eventDAO.existsByPlaceAndDate(payload.place(), payload.date())) {
            Event newEvent = new Event(payload.title(), payload.description(), payload.place(), payload.date(), payload.maxPartecipants());
            return this.eventDAO.save(newEvent);
        } else throw new BadRequestException("Place and Date are already booked");
    }

    //    DO LA POSSIBILITA' DI CAMBIARE IL LUOGO E LA DATA DELL'EVENTO DOPO CHE E' STATO CREATO, IN QUANTO DIFFICILMENTE VERRANNO MODIFICATE ALTRE VARIABILI IN UNA CASISTICA REALE
    public Event findByIdAndUpdate(long id, UpdateEventDTO payload) {
        Event found = this.findById(id);
        if (!eventDAO.existsByPlaceAndDate(payload.place(), payload.date())) {
            found.setDate(payload.date());
            found.setPlace(payload.place());
            return this.eventDAO.save(found);
        } else throw new BadRequestException("Place and Date are already booked");
    }
}
