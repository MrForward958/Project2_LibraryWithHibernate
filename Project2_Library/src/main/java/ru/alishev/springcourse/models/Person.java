package ru.alishev.springcourse.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Name can`t be empty!")
    @Size(min = 2,max = 50, message = "Name should be between 2 and 50 characters")
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "birth_day")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;
    @Email(message = "Email must have format like email")
    @Column(name = "email")
    @NotEmpty(message = "Email should not be empty!")
    private String email;
    @Column(name = "address")
    @NotEmpty(message = "Address should not be empty")
    //@Pattern(regexp = "[A-z]\\w+")
    private String address;
    @Column(name = "registration_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationTime;
    @OneToMany(mappedBy = "owner")
    private List<Book> bookList;

    public Person() {
    }

    public Person(String fullName, Date birthDay, String email, String address, List<Book> bookList) {
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.email = email;
        this.address = address;
        this.bookList = bookList;
    }

    public Person(String fullName, Date birthDay, String email, String address) {
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", birthDay=" + birthDay +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", registrationTime=" + registrationTime +
                '}';
    }
}
