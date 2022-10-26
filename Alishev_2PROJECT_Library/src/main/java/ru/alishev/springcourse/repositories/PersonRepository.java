package ru.alishev.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.models.Person;


@Repository
public interface PersonRepository extends JpaRepository <Person, Integer> {
    Person findByFullName(String name);
    Person findByEmail(String email);
}
