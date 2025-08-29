package library;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {
    User userId;
    Book bookId;
    LocalDate loanDate;
    LocalDate returnDate;
    private boolean returned = false;

    public Loan(User user, Book book){
        this.userId = user;
        this.bookId = book;
        this.loanDate = LocalDate.now();
        organizeReturnDate();
    }

    public User getUser() { return userId; }
    public Book getBook() { return bookId; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean isReturned() { return returned; }

    public boolean isOverdue(){
        return LocalDate.now().isAfter(returnDate);
    }

    public void organizeReturnDate(){
        LocalDate today = LocalDate.now();
        LocalDate calculatedReturnDate = today;
        int businessDays = 0;

        while (businessDays < 7) {
            calculatedReturnDate = calculatedReturnDate.plusDays(1);
            DayOfWeek dayOfWeek = calculatedReturnDate.getDayOfWeek();

            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                businessDays++;
            }
        }

        this.returnDate = calculatedReturnDate;

        System.out.println("Data de devolução: " + returnDate);

    }

    public void markReturned(){
        if(returned){
            System.out.println("Este livro já foi devolvido.");
        }
        this.returned = true;

        if(isOverdue()){
            long daysLate = ChronoUnit.DAYS.between(returnDate, LocalDate.now());
            double fee = Fees.calculateFee(daysLate);
            System.out.println("Livro devolvido com atraso de " + daysLate + " dias.");
            System.out.println("Taxa a ser paga: R$ " + String.format("%.2f", fee));
        } else{
            System.out.println("Livro devolvido no prazo. Agradecemos.");
        }
    }

}
