package library;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Library {

    private List<Book> books = new ArrayList<>();
    private List <Author> authors = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);

    public void handleUserLoginFlow(){
        System.out.println("Você já é cadastrado? (sim/nao)");
        String answer = scanner.next();
        User newUser = new User();

        if(answer.equalsIgnoreCase("nao")){
            newUser.insertNewUserData();
            users.add(newUser);
        } else {
            System.out.println("Digite seu email: ");
            String email = scanner.next();

            boolean found = false;

            for(User user : users){
                if(user.getEmail().equalsIgnoreCase(email)){
                    System.out.println("Bem vindo(a) de volta, " + user.getName());
                    found = true;
                    break;
                }
            }

            if(!found){
                System.out.println("Usuário não encontrado. Vamos fazer seu cadastro");
                newUser.insertNewUserData();
                users.add(newUser);
            }
        }
    }

    public void handleUserList(){
        System.out.println("Gostaria de ver a lista de usuários cadastrados?(sim/nao)");
        String answer = scanner.next();

        if(answer.equalsIgnoreCase("nao")){
            System.out.println("Gratidão por se cadastrar conosco.");
        } else{
           showAllUsers();
        }

    }

    public void showAllUsers(){
        System.out.println("\n====== LISTA DE USUÁRIOS CADASTRADOS ======");
        if(users.isEmpty()){
            System.out.println("Nenhum usuário foi cadastrado.");
        } else{
            for(User user : users){
                System.out.println("Nome: " + user.getName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Data de nascimento: " + user.getBirthday());
                System.out.println("--------------------------------------");
            }
        }
    }

}
