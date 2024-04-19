package FabioGilardi.U5W3D5.controllers;

import FabioGilardi.U5W3D5.entities.Event;
import FabioGilardi.U5W3D5.exceptions.BadRequestException;
import FabioGilardi.U5W3D5.payloads.NewEventDTO;
import FabioGilardi.U5W3D5.payloads.UpdateEventDTO;
import FabioGilardi.U5W3D5.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    //    LA RICERCA DEGLI EVENTI L'HO LASCIATA APERTA A TUTTI IN QUANTO ANCHE GLI USER NORMALI POSSONO VEDERE QUALI EVENTI SONO DISPONIBILI
    @GetMapping
    public Page<Event> findAll(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int size,
                               @RequestParam(defaultValue = "id") String sortBy) {
        return this.eventService.findAll(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Event findById(@PathVariable long id) {
        return eventService.findById(id);
    }

    //    LA MODIFICA/AGGIUNTA/ELIMINAZIONE DI EVENTI E' RISERVATA AGLI EVENT_ORGANIZER SOLTANTO
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('EVENT_ORGANIZER')")
    public Event save(@RequestBody @Validated NewEventDTO payload,
                      BindingResult validation) {
        if (validation.hasErrors()) throw new BadRequestException(validation.getAllErrors());
        return this.eventService.save(payload);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EVENT_ORGANIZER')")
    public Event findByIdAndUpdate(@PathVariable long id,
                                   @RequestBody @Validated UpdateEventDTO payload,
                                   BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.eventService.findByIdAndUpdate(id, payload);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EVENT_ORGANIZER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable long id) {
        this.eventService.findByIdAndDelete(id);
    }
}
