package library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class User {
    long userId;
    String name;
    LocalDate birthday;
    String email;

     List<Loan> loans = new ArrayList<>();
     double pendingFees = 0.0;
     boolean blocked = false;

    Scanner scanner = new Scanner(System.in);

    public Long getUserId() { return this.userId; }
    public String getName() { return this.name; }
    public LocalDate getBirthday(){ return birthday;}
    public String getEmail() { return this.email; }
    public List<Loan> getLoans() { return loans; }
    public void addLoan(Loan loan) { loans.add(loan); }
    public double getPendingFees() { return pendingFees; }
    public void setPendingFees(double pendingFees) { this.pendingFees = pendingFees; }
    public boolean isBlocked() { return blocked; }
    public void setBlocked(boolean blocked) { this.blocked = blocked; }


    public String readLineSafe(){
        String input;
        do {
            input = scanner.nextLine().trim();
        } while (input.isEmpty());
        return input;
    }

    public void insertNewUserData(){

       System.out.println("Insira seu nome");

       name = readLineSafe();

       System.out.println("Insira sua data de nascimento");
       String input = readLineSafe();
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       birthday = LocalDate.parse(input, formatter);

       System.out.println("Insira seu email");

       email = readLineSafe();

       Random random = new Random();
        userId = 100000L + (Math.abs(random.nextLong()) % 900000L);

        System.out.printf("Bem vindo(a), %s! Seu ID de usuário é %d%n", name, userId);

   }

    public void applyFee(double feeAmount) {
        pendingFees += feeAmount;
        if (pendingFees > 0) {
            blocked = true;
        }
    }

    public void payFee(double amount) {
        if (amount >= pendingFees) {
            pendingFees = 0;
            blocked = false;
            System.out.println("Multa quitada! Agora você pode realizar empréstimos normalmente.");
        } else {
            pendingFees -= amount;
            System.out.printf("Você pagou R$ %.2f. Ainda restam R$ %.2f de multa.%n", amount, pendingFees);
        }
    }

    public void checkFeeStatus() {
        if (pendingFees > 0) {
            System.out.printf("Você possui multa pendente de R$ %.2f%n", pendingFees);
            if (blocked) {
                System.out.println("Seu usuário está bloqueado até quitar a multa.");
            }
        } else {
            System.out.println("Você não possui multas pendentes.");
        }
    }

}
