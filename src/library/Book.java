package library;

import java.time.LocalDate;

public class Book {
    Integer bookId;
    String title;
    Author author;
    Boolean available;
    LocalDate DateRegister;
    LocalDate DateReturned;

    public String getTitle() { return title;    }

    public void setTitle(String title) { this.title = title; }

    public Author getAuthor() { return author; }

    public void setAuthor(Author author) { this.author = author; }

    public Boolean getAvailable() { return available; }

    public LocalDate getDateRegister() { return DateRegister;   }

    public LocalDate getDateReturned() {  return DateReturned;  }

}
