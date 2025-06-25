package library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class User {
    long userId;
    String name;
    LocalDate birthday;
    String email;

    Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

    public Long getUserId() { return this.userId; }
    public String getName() { return this.name; }
    public LocalDate getBirthday(){ return birthday;}
    public String getEmail() { return this.email; }

    public void insertNewUserData(){


       System.out.println("Insira seu nome");

       name = scanner.next();

       System.out.println("Insira sua data de nascimento");
       String input = scanner.next();
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       birthday = LocalDate.parse(input, formatter);


       System.out.println("Insira seu email");

       email = scanner.next();

       Random random = new Random();
       userId = 100000L + random.nextLong(900000L);

       System.out.printf("Bem vindo(a), %s! Seu ID de usuário é %d%n", name, userId);

   }

}
