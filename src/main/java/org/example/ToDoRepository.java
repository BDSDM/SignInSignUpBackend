package org.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    @Query(value = "SELECT td.* FROM todos td INNER JOIN users u ON u.id = td.user_id WHERE u.username = :username", nativeQuery = true)
    List<ToDo> findToDoByUsername(@Param("username") String username);
}
