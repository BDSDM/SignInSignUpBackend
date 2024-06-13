package org.example;


import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "http://localhost:4200")
public class ToDoController {

    @Autowired
    private ToDoRepository toDoRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{username}")
    public List<ToDo> getAllToDosByUser(@PathVariable String username) {
        return toDoRepository.findToDoByUsername(username);
    }
    @GetMapping
    public List<ToDo> getAllToDos() {
        return toDoRepository.findAll();
    }
    @PostMapping("/{username}")
    public ToDo createToDo(@PathVariable String username, @RequestBody ToDo toDo) {
        User user = userRepository.findByUsername(username);
        toDo.setUser(user);
        return toDoRepository.save(toDo);
    }

    @PutMapping("/{username}/{id}")
    public ToDo updateToDo(@PathVariable String username, @PathVariable Long id, @RequestBody ToDo toDoDetails) {
        ToDo toDo = toDoRepository.findById(id).orElseThrow(() -> new RuntimeException("ToDo not found"));
        if (!toDo.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized");
        }
        toDo.setTitle(toDoDetails.getTitle());
        toDo.setCompleted(toDoDetails.isCompleted());
        return toDoRepository.save(toDo);
    }

    @DeleteMapping("/{username}/{id}")
    public void deleteToDo(@PathVariable String username, @PathVariable Long id) {
        ToDo toDo = toDoRepository.findById(id).orElseThrow(() -> new RuntimeException("ToDo not found"));
        if (!toDo.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized");
        }
        toDoRepository.delete(toDo);
    }
}
