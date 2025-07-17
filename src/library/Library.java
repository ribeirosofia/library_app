package library;

import javax.naming.InvalidNameException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public String readLineSafe(){
       if(scanner.hasNextLine()){
           return scanner.nextLine();
       }
       return "";
    }

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
            System.out.println("Vamos te direcionar a página de livros.");
            registerBookByAuthor();
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

    public void listAllBooks(){
        System.out.println("\n====== LISTA DE LIVROS CADASTRADOS ======");
        if(books.isEmpty()){
            System.out.println("A lista de livros está vazia.");
        } else{
            for(Book book : books){
                System.out.println("Título: " + book.getTitle());
                System.out.println("Autor: " + book.getAuthor());
                System.out.println("Data de registro: " + book.getDateRegister());

                if(book.available){
                    System.out.println("Livro disponível.");
                } else{
                    System.out.println("Livro indisponível.");
                }

            }
        }
    }

    public void registerBookByAuthor(){
        System.out.println("Insira o nome do autor que você procura");
        String name = readLineSafe();

        boolean found = false;

        Author selectedAuthor = null;

        for(Author author : authors){
            if(author.getName().equalsIgnoreCase(name)){
                selectedAuthor = author;
                System.out.println("Autor/autora de nome " + name + " foi encontrado");
            }
        }

        if(selectedAuthor == null){
            System.out.println("Autor/autora não encontrado.");
           selectedAuthor = handleAuthorFlow();
        }

        System.out.println("Insira o nome do livro.");

        String bookTitle = readLineSafe();

        Book newBook = new Book();
        newBook.setTitle(bookTitle);
        newBook.setAuthor(selectedAuthor);
        books.add(newBook);
        System.out.println("Você inseriu o livro " + bookTitle + " do autor/autora " + selectedAuthor + " na lista.");
    }

    public void registerAnotherBookByAuthor(){
        System.out.println("Gostaria de inserir outro livro? (sim/nao)");
        String answer = scanner.nextLine();
        if(answer.equalsIgnoreCase("nao")){
            System.out.println("Já vamos te passar para a página de empréstimos.");
            //
        } else{
            registerBookByAuthor();
        }
    }

    public void findAuthorByName(){
        System.out.println("Insira o nome do autor/autora do livro que procura.");
        String name = readLineSafe();

        List<String> titles = new ArrayList<>();

        for(Book book : books){
            if(book.getAuthor().getName().equalsIgnoreCase(name)){
                titles.add(book.getTitle());
            }
        }

        if(titles.isEmpty()){
            System.out.println("Autor/autora não encontrado. Vamos registrar no sistema.");
            handleAuthorFlow();
        } else{
            System.out.println("Aqui estão os livros feitos pelo autor/autora: ");
            for(String title : titles){
                System.out.println("- " + title);
            }
        }

    }


    public Author handleAuthorFlow(){
            System.out.println("Insira o nome do autor/autora");

            scanner.nextLine();
            String authorName = readLineSafe();

            System.out.println("Insira a data de nascimento do autor");
            String input = readLineSafe();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate authorBirthday = LocalDate.parse(input, formatter);

            Author newAuthor = new Author();
            newAuthor.setName(authorName);
            newAuthor.setBirthdayDate(authorBirthday);
            authors.add(newAuthor);
            System.out.println("Você inseriu " + authorName + " como autor/autora. " +
                    "\n Sua data de nascimento é em " + authorBirthday);
            return newAuthor;
    }

    public void insertAnotherAuthor(){
        System.out.println("Gostaria de inserir outro autor? (sim/nao)");
        String answer = scanner.next();
        if(answer.equalsIgnoreCase("nao")){
            System.out.println("Já vamos te passar para a página de livros.");
            registerBookByAuthor();
        } else{
            handleAuthorFlow();
        }
    }

}
