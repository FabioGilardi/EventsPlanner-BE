package FabioGilardi.U5W3D5.controllers;

import FabioGilardi.U5W3D5.entities.Booking;
import FabioGilardi.U5W3D5.entities.User;
import FabioGilardi.U5W3D5.exceptions.BadRequestException;
import FabioGilardi.U5W3D5.payloads.NewBookingDTO;
import FabioGilardi.U5W3D5.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/me")
    public List<Booking> findAll(@AuthenticationPrincipal User currentUser) {
        return bookingService.findAllOneUser(currentUser.getId());
    }

    @GetMapping("/me/{id}")
    public Booking findById(@AuthenticationPrincipal User currentUser,
                            @PathVariable long id) {
        return bookingService.findById(id, currentUser.getId());
    }

    @DeleteMapping("/me/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@AuthenticationPrincipal User currentUser,
                                  @PathVariable long id) {
        bookingService.findByIdAndDelete(id, currentUser.getId());
    }

    @PostMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    public Booking save(@RequestBody @Validated NewBookingDTO payload,
                        @AuthenticationPrincipal User currentUser,
                        BindingResult validation) {
        if (validation.hasErrors()) throw new BadRequestException(validation.getAllErrors());
        return bookingService.save(currentUser.getId(), payload);
    }
}
