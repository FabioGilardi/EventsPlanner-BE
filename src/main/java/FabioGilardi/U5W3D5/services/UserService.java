package FabioGilardi.U5W3D5.services;

import FabioGilardi.U5W3D5.entities.User;
import FabioGilardi.U5W3D5.exceptions.BadRequestException;
import FabioGilardi.U5W3D5.exceptions.NotFoundException;
import FabioGilardi.U5W3D5.payloads.NewUserDTO;
import FabioGilardi.U5W3D5.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public User save(NewUserDTO payload) {
        if (!userDAO.existsByEmail(payload.email()) && !userDAO.existsByUsername(payload.username())) {
            User newUser = new User(payload.name(), payload.surname(), payload.email(), payload.username(), payload.password());
            return this.userDAO.save(newUser);
        } else throw new BadRequestException("Username/Email are already taken");
    }

    public Page<User> findAll(int number, int size, String sortBY) {
        Pageable pageable = PageRequest.of(number, size, Sort.by(sortBY));
        return this.userDAO.findAll(pageable);
    }

    public User findById(long id) {
        return this.userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByIdAndUpdate(long id, NewUserDTO payload) {
        User found = this.findById(id);
        if (found.getEmail().equals(payload.email())) {
            if (found.getUsername().equals(payload.username())) {
                found.setName(payload.name());
                found.setSurname(payload.surname());
            } else if (!this.userDAO.existsByUsername(payload.username())) {
                found.setUsername(payload.username());
                found.setName(payload.name());
                found.setSurname(payload.surname());
            } else throw new BadRequestException("Username " + payload.username() + " is already taken");
            this.userDAO.save(found);
            return found;
        } else throw new BadRequestException("You are not allowed to change the email");
    }

    public void findByIdAndDelete(long id) {
        User found = this.findById(id);
        this.userDAO.delete(found);
    }
}
