package ru.alishev.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.alishev.springcourse.models.Person;
import ru.alishev.springcourse.service.BookService;
import ru.alishev.springcourse.service.PersonService;
import ru.alishev.springcourse.until.PersonValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private final BookService bookService;
    private final PersonValidator personValidator;

    public PersonController(PersonService personService, BookService bookService, PersonValidator personValidator) {
        this.personService = personService;
        this.bookService = bookService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model, HttpServletRequest request){
        String page = request.getParameter("page");
        String booksPerPage = request.getParameter("people_per_page");
        String sort = request.getParameter("sort_by_fullName");
        if(page != null && booksPerPage != null){
            model.addAttribute("people", personService.findAll(page,booksPerPage,sort));
        }else {
            model.addAttribute("people", personService.findAll(sort));
        }
        return "person/listPerson";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable ("id") int id, Model model) {
        Person person = personService.findById(id);
        model.addAttribute("person", person);
        model.addAttribute("books",bookService.findByOwner(person));
        return "person/showPerson";
    }
    @GetMapping("/new")
    public String createPerson(@ModelAttribute("person")Person person){
        return "person/new";
    }

    @PostMapping()
    public String savePerson(@ModelAttribute("person") @Valid Person person,
                             BindingResult bindingResult){
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors())
            return "person/new";
        personService.save(person);
        return "redirect:/person";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("updatePerson", personService.findById(id));
        return "person/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id")int id, @ModelAttribute @Valid Person updatePerson,
                               BindingResult bindingResult ){
        if(bindingResult.hasErrors())
            return "person/new";
        personService.update(id,updatePerson);
        return "redirect:/person";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id")int id){
        personService.delete(id);
        return "redirect:/person";
    }

}
