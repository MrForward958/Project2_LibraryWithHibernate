package ru.alishev.springcourse.until;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.alishev.springcourse.models.Person;
import ru.alishev.springcourse.service.PersonService;

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;

    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (personService.findByName(person.getFullName()) != null)
            errors.rejectValue("fullName", "", "Name should be unique");
        if(person.getBirthDay() == null)
            errors.rejectValue("birthDay","","Birth date should be not empty");
        if(personService.findByEmail(person.getEmail()) != null)
            errors.rejectValue("email","","Email should be unique");
    }
}
