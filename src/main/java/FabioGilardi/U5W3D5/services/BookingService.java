package FabioGilardi.U5W3D5.services;

import FabioGilardi.U5W3D5.entities.Booking;
import FabioGilardi.U5W3D5.exceptions.BadRequestException;
import FabioGilardi.U5W3D5.exceptions.NotFoundException;
import FabioGilardi.U5W3D5.payloads.NewBookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingDAO bookingDAO;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    //    FA IN MODO CHE L'UTENTE LOGGATO POSSA PRENOTARE SOLO PER SE STESSO E NON PER ALTRI
    public Booking save(long userId, NewBookingDTO payload) {
        if (bookingDAO.existsByEventIdAndUserId(payload.eventId(), userId))
            throw new BadRequestException("You have already booked for this event");
        if (eventService.findById(payload.eventId()).getBookingList().size() >= eventService.findById(payload.eventId()).getMaxPartecipants())
            throw new BadRequestException("Event " + payload.eventId() + " is already full");
        Booking newBooking = new Booking(this.userService.findById(userId), this.eventService.findById(payload.eventId()));
        return this.bookingDAO.save(newBooking);
    }

    //    FA IN MODO CHE L'UTENTE LOGGATO POSSA VEDERE TUTTE LE SUE PRENOTAZIONI, NON AGGIUNGO UN FINDALL GENERICO IN QUANTO NON MI SEMBRA NECESSARIA UNA FUNZIONE CHE MOSTRI TUTTE LE PRENOTAZIONI PER OGNI SINGOLO UTENTE ED EVENTO
    //    HO USATO LIST IN QUANTO IL NUMERO DI PRENOTAZIONI DI UN SINGOLO UTENTE SARA' SEMPRE CONTENUTO
    public List<Booking> findAllOneUser(long userId) {
        return this.bookingDAO.findByUserId(userId);
    }

    //    FA IN MODO CHE L'UTENTE POSSA CERCARE PER ID SOLO LE SUE PRENOTAZIONI
    public Booking findById(long id, long userId) {
        return this.bookingDAO.findByIdAndUserId(id, userId).orElseThrow(() -> new NotFoundException(id));
    }

    //    FA IN MODO CHE L'UTENTE POSSA CANCELLARE SOLO LE SUE PRENOTAZIONI
    public void findByIdAndDelete(long id, long userId) {
        Booking found = this.findById(id, userId);
        this.bookingDAO.delete(found);
    }

//    NON INSERISCO L'UPDATE IN QUANTO LA PRENOTAZIONE E' UNIVOCA E NON C'E' NULLA DA CAMBIARE
}
