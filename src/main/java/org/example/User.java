package org.example;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users") // Annotation @Table pour spécifier le nom de la table dans la base de données
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String username;
    private String password;

    // Constructeur par défaut
    public User() {
    }

    // Constructeur avec tous les attributs
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters et setters pour tous les attributs

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
