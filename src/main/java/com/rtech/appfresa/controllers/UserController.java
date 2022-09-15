package com.rtech.appfresa.controllers;

import com.rtech.appfresa.entities.User;
import com.rtech.appfresa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping(value = "/listUsers")
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping(value = "/saveUser")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping(value = "/validarSenha")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String login,
                                                @RequestParam String password) {


        Optional<User> optUser = userRepository.findByLogin(login);
        if (optUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }



        User user = optUser.get();
        boolean valid = encoder.matches(password, user.getPassword());

        HttpStatus status = (valid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(status).body(valid);


    }
}
