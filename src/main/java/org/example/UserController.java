package org.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            // Renvoie un statut HTTP 409 (Conflict) si l'utilisateur existe déjà
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("message", "Utilisateur déjà existant"));
        }
        userRepository.save(user);
        // Renvoie un statut HTTP 200 (OK) avec un message de succès si l'enregistrement est réussi
        return ResponseEntity.ok(Collections.singletonMap("message", "Utilisateur enregistré avec succès"));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser == null || !foundUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Identifiant ou mot de passe incorrect"));
        }
        // Authentification réussie, renvoyer une réponse appropriée
        return ResponseEntity.ok().body(Collections.singletonMap("message", "Connexion réussie"));
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
