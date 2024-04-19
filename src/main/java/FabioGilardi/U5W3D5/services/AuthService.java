package FabioGilardi.U5W3D5.services;

import FabioGilardi.U5W3D5.entities.User;
import FabioGilardi.U5W3D5.exceptions.BadRequestException;
import FabioGilardi.U5W3D5.exceptions.UnauthorizedException;
import FabioGilardi.U5W3D5.payloads.NewUserDTO;
import FabioGilardi.U5W3D5.payloads.UserLoginDTO;
import FabioGilardi.U5W3D5.repositories.UserDAO;
import FabioGilardi.U5W3D5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserDAO userDAO;

    public User save(NewUserDTO payload) {
        if (!userDAO.existsByEmail(payload.email()) && !userDAO.existsByUsername(payload.username())) {
            User newUser = new User(payload.name(), payload.surname(), payload.email(), payload.username(), encoder.encode(payload.password()));
            return this.userDAO.save(newUser);
        } else throw new BadRequestException("Username/Email are already taken");
    }

    public String authUserAndCreateToken(UserLoginDTO payload) {
        User user = userService.findByEmail(payload.email());
        if (encoder.matches(payload.password(), user.getPassword()))
            return jwtTools.createToken(user);
        else
            throw new UnauthorizedException("Password is wrong");
    }
}
