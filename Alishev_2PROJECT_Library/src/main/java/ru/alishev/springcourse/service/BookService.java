package ru.alishev.springcourse.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Person;
import ru.alishev.springcourse.repositories.BookRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(String sort){
        if(sort!=null) {
            return bookRepository.findAll(Sort.by("dateRelease"));
        }else {
            return bookRepository.findAll();
        }
    }

    public List<Book> findAll(String page, String  booksPerPage, String sort){
        int intPage = Integer.parseInt(page);
        int intBooksPerPage = Integer.parseInt(booksPerPage);
        if (sort != null){
            return bookRepository.findAll(PageRequest.of(intPage, intBooksPerPage,
                    Sort.by("dateRelease"))).getContent();
        }else {
            return bookRepository.findAll(PageRequest.of(intPage, intBooksPerPage)).getContent();
        }
    }

    public Book findById(int id){
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }

    public List<Book> findByOwner(Person owner){
        List<Book> books = bookRepository.findByOwner(owner);
        if(books != null){
            books.forEach(book -> book.setOverdue(
                    (new Date().getTime() - book.getAppointmentTime().getTime()) / 1000 > 864000));
        }
        return books;
    }

    public List<Book> findByNameStartingWith(String starBy){
        return bookRepository.findByNameStartingWith(starBy);
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void update(Book updateBook,int id){
        updateBook.setId(id);
        bookRepository.save(updateBook);
    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }

    @Transactional
    public void release(int id){
        bookRepository.findById(id).ifPresent(book -> {
            book.setOwner(null);
            book.setAppointmentTime(null);
        });

    }

    @Transactional
    public void assignBook(int id, Person person){
        bookRepository.findById(id).ifPresent(book -> {
            book.setOwner(person);
            book.setAppointmentTime(new Date());
        });
    }
}
