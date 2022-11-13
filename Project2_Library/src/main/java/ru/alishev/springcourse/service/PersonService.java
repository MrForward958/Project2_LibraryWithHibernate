package ru.alishev.springcourse.service;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.models.Person;
import ru.alishev.springcourse.repositories.PersonRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll(String sort){
        if (sort != null) {
            return personRepository.findAll(Sort.by("fullName"));
        }else
            return personRepository.findAll();
    }


    public List<Person> findAll(String page, String booksPerPage, String sort){
        int intPage = Integer.parseInt(page);
        int intBooksPerPage = Integer.parseInt(booksPerPage);
        if (sort != null) {
            return personRepository.findAll(PageRequest.of(intPage, intBooksPerPage,
                    Sort.by("fullName"))).getContent();
        }else {
            return personRepository.findAll(PageRequest.of(intPage,intBooksPerPage)).getContent();
        }
    }

    public Person findById(int id){
        Optional<Person> foundPerson = personRepository.findById(id);
        return foundPerson.orElse(null);
    }

    //Для валидатора
    public Person findByName(String name){
        return personRepository.findByFullName(name);
    }

    //Для валидатора
    public Person findByEmail(String email){
        return personRepository.findByEmail(email);
    }

    @Transactional
    public void save(Person person){
        person.setRegistrationTime(new Date());
        personRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatePerson) {
        Date registrationOld = personRepository.findById(id).get().getRegistrationTime();
        updatePerson.setRegistrationTime(registrationOld);
        updatePerson.setId(id);
        personRepository.save(updatePerson);
    }

    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }
}
