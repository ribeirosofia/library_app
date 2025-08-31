package library;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {
    User userId;
    Book bookId;
    LocalDate loanDate;
    LocalDate dueDate;
    LocalDate returnDate;
    private boolean returned = false;

    public Loan(User user, Book book) {
        this.userId = user;
        this.bookId = book;
        this.loanDate = LocalDate.now();
        this.dueDate = calculateDueDate();
        this.returnDate = null;
    }

    public User getUser() {
        return userId;
    }

    public Book getBook() {
        return bookId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(returnDate);
    }

    public LocalDate calculateDueDate() {
        LocalDate calculatedReturnDate = loanDate;
        int businessDays = 0;

        while (businessDays < 7) {
            calculatedReturnDate = calculatedReturnDate.plusDays(1);
            DayOfWeek dayOfWeek = calculatedReturnDate.getDayOfWeek();

            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                businessDays++;
            }
        }

        return calculatedReturnDate;
    }

    public void returnBook() {
        this.returnDate = LocalDate.now();
        System.out.println("Livro devolvido em: " + returnDate);

        if (returnDate.isAfter(dueDate)) {
            System.out.println("⚠️ Devolução atrasada! O prazo era " + dueDate);
        } else {
            System.out.println("✅ Livro devolvido dentro do prazo.");
        }
    }
}
