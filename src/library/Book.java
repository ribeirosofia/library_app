package library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Book {
    long bookId;
    String title;
    Author author;
    Boolean available;
    LocalDate dateRegister;
    LocalDate dateReturned;

    public Book(){
        this.dateRegister = LocalDate.now();
    }

    public long getBookId() { return bookId;    }

    public String getTitle() { return title;    }

    public void setTitle(String title) { this.title = title; }

    public Author getAuthor() { return author; }

    public void setAuthor(Author author) { this.author = author; }

    public Boolean getAvailable() { return available; }

    public LocalDate getDateRegister() { return dateRegister;   }

    public LocalDate getDateReturned() {  return dateReturned;  }

    public void bookData(){
        Random random = new Random();
        bookId = 100000L + (Math.abs(random.nextLong()) % 900000L);
        System.out.printf("O ID do livro " + title + " é " + bookId + ". ");
    }

    public String getDateRegisterFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateRegister.format(formatter);
    }

    @Override
    public String toString() {
        return "Título: " + title +
                "\nAutor: " + author.getName() +
                "\nId: " + this.hashCode() +
                "\nData de registro: " + getDateRegisterFormatted();
    }

}
