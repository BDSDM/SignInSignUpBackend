package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/identities")
public class IdentityController {

    @Autowired
    private IdentityRepository identityRepository;

    @Autowired
    private UserRepository userRepository;

    // Endpoint pour récupérer une identité par username
    @GetMapping("/{username}")
    public ResponseEntity<Identity> getIdentityByUsername(@PathVariable String username) {
        Identity identity = identityRepository.findByUser_Username(username);
        if (identity != null) {
            return ResponseEntity.ok(identity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint pour créer une identité pour un utilisateur par username
    @PostMapping("/{username}")
    public ResponseEntity<Identity> createIdentity(@PathVariable String username, @RequestBody Identity identity) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Vérifier si l'utilisateur a déjà une identité
        Identity existingIdentity = identityRepository.findByUser_Username(username);
        if (existingIdentity != null) {
            return ResponseEntity.status(409).body(existingIdentity);
        }

        identity.setUser(user);
        Identity savedIdentity = identityRepository.save(identity);
        return ResponseEntity.ok(savedIdentity);
    }

    // Endpoint pour mettre à jour une identité par username
    @PutMapping("/{username}")
    public ResponseEntity<Identity> updateIdentityByUsername(@PathVariable String username, @RequestBody Identity updatedIdentity) {
        Identity identity = identityRepository.findByUser_Username(username);
        if (identity == null) {
            return ResponseEntity.notFound().build();
        }

        identity.setFirstName(updatedIdentity.getFirstName());
        identity.setLastName(updatedIdentity.getLastName());
        identity.setEmail(updatedIdentity.getEmail());
        identity.setPhoneNumber(updatedIdentity.getPhoneNumber());
        identityRepository.save(identity);

        return ResponseEntity.ok(identity);
    }

    // Endpoint pour supprimer une identité par username
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteIdentityByUsername(@PathVariable String username) {
        Identity identity = identityRepository.findByUser_Username(username);
        if (identity != null) {
            identityRepository.delete(identity);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
