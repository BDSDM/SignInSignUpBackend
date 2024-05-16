package org.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    // Ajouter un endpoint pour afficher tous les utilisateurs
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "Utilisateur non trouvé"));
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Utilisateur supprimé avec succès"));
    }
}
