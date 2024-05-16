package org.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public Object registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Utilisateur déjà existant"));
        }
        userRepository.save(user);
        return ResponseEntity.ok().body(Collections.singletonMap("message", "Utilisateur enregistré avec succès"));
    }

    @PostMapping("/login")
    public Object loginUser(@RequestBody User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null || !existingUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Nom d'utilisateur ou mot de passe incorrect"));
        }
        return ResponseEntity.ok().body(Collections.singletonMap("message", "Authentification réussie"));
    }
}
