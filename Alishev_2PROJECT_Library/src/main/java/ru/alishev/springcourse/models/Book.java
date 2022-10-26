package ru.alishev.springcourse.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Name of book can`t  be empty!")
    @Size(min = 2, max = 30, message = "Name of book should be between 2 and 30 characters!")
    @Column(name = "name")
    private String name;
    @NotEmpty(message = "Name of author can`t be empty!")
    @Size(min = 2,max = 50, message = "Name of author should be between 2 and 50 characters!")
    @Column(name = "author")
    private String author;
    //Проверить как работает этот выпадающий календарь
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "release")
    private Date dateRelease;
    @Column(name = "appointment_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentTime;
    @Transient
    private boolean overdue;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book() {
    }

    public Book(String name, String author, Date dateRelease, Person owner) {
        this.name = name;
        this.author = author;
        this.dateRelease = dateRelease;
        this.owner = owner;
    }

    public Book(String name, String author, Date dateRelease) {
        this.name = name;
        this.author = author;
        this.dateRelease = dateRelease;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDateRelease() {
        return dateRelease;
    }

    public void setDateRelease(Date release) {
        this.dateRelease = release;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", release=" + dateRelease +
                '}';
    }
}
