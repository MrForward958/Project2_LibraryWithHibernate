package ru.alishev.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Person;
import ru.alishev.springcourse.service.BookService;
import ru.alishev.springcourse.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;

    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping()
    public String index(Model model, HttpServletRequest request){
        String page = request.getParameter("page");
        String booksPerPage = request.getParameter("books_per_page");
        String sort = request.getParameter("sort_by_year");
        if(page != null && booksPerPage != null) {
            model.addAttribute("books", bookService.findAll(page, booksPerPage, sort));
        }else {
            model.addAttribute("books", bookService.findAll(sort));
        }
        return "book/listBook";
    }


    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model,
                           @ModelAttribute("person") Person person){
        Book book = bookService.findById(id);
        if (book == null) {
            return "redirect:/book";
        }
        model.addAttribute("book", book);
        if (book.getOwner() == null) {
            model.addAttribute("people", personService.findAll(null));
        } else {
            model.addAttribute("owner", personService.findById(book.getOwner().getId()));
        }
        return "book/showBook";
    }


    @GetMapping ("/search")
    public String searchBook(Model model,@RequestParam(value = "request", required = false) String request ) {
        //Может быть баг т.к. после первого поиска в модели останется значение, и если второй раз перейти на search, он выполнит поиск.
        if (request != null && !request.equals("")){
            model.addAttribute( "bookIsFind", bookService.findByNameStartingWith(request));
        }
        return "book/search";
    }

    @GetMapping("/new")
    public String createBook(@ModelAttribute("book") Book book) {
        //Возвращаем форму на создание нового человека
        return "book/new";
    }

    @PostMapping()
    public String saveBook(@ModelAttribute("book") @Valid Book book,
                           BindingResult bindingResult){
        //проверяем есть ли ошибки валидации, если есть возвращаем форму заново.
        if(bindingResult.hasErrors())
            return "book/new";
        //Если ошибок нет отправляем SQL запрос на добавление книги в БД.
        bookService.save(book);
        return "redirect:/book";
    }

    //Адрес в виде ID, для того что бы получить ID книги, которую редактируем из запроса,
    //и далее получить ее с БД с помощью ID - bookDAO.show(id)
    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model){
        //Получаем с помощью метода show книгу по id, и передаем его через модель в форму для редактирования.
        model.addAttribute("book",bookService.findById(id));
        //возвращаем форму
        return "book/edit";
    }

    //Адрес в виде ID, для того что бы указать книгу с каким ID заменяем в БД.
    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "book/edit";
        bookService.update(book,id);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/book";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id){
        bookService.release(id);
        return "redirect:/book/{id}";
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("person")Person person){
        bookService.assignBook(id,person);
        return "redirect:/book/{id}";
    }

}
