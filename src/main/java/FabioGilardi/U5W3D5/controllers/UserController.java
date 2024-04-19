package FabioGilardi.U5W3D5.controllers;

import FabioGilardi.U5W3D5.entities.User;
import FabioGilardi.U5W3D5.exceptions.BadRequestException;
import FabioGilardi.U5W3D5.payloads.NewUserDTO;
import FabioGilardi.U5W3D5.services.AuthService;
import FabioGilardi.U5W3D5.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

//    HO INSERITO SOLO I METODI ME IN QUANTO GLI EVENT_ORGANIZER NON HANNO FUNZIONE DI ADMIN E QUINDI LA POSSIBILITA' DI VISIONARE/MODIFICARE/ELIMINARE GLI ALTRI UTENTI

    @GetMapping("/me")
    public User findMe(@AuthenticationPrincipal User currentUser) {
        return currentUser;
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMe(@AuthenticationPrincipal User currentUser) {
        this.userService.findByIdAndDelete(currentUser.getId());
    }

    @PutMapping("/me")
    public User modifyMe(@AuthenticationPrincipal User currentUser,
                         @RequestBody @Validated NewUserDTO payload,
                         BindingResult validation) {
        if (validation.hasErrors()) throw new BadRequestException(validation.getAllErrors());
        return this.userService.findByIdAndUpdate(currentUser.getId(), payload);
    }
}
