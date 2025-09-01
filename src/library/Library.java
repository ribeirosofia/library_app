package library;

import org.w3c.dom.ls.LSOutput;

import javax.naming.InvalidNameException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Library {

    private List<Book> books = new ArrayList<>();
    private List <Author> authors = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);

    public String readLineSafe(){
        String input;
        do {
            input = scanner.nextLine().trim();
        } while (input.isEmpty());
        return input;
    }

    public void handleUserLoginFlow(){
        System.out.println("Seja bem vindo a página de login.");
        System.out.println("Você já é cadastrado? (sim/nao)");
        String answer = scanner.next();
        User newUser = new User();

        if(answer.equalsIgnoreCase("nao")){
            newUser.insertNewUserData();
            users.add(newUser);
        } else {
            System.out.println("Digite seu email: ");
            String email = readLineSafe();

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
            checkBooksList();
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
        checkBooksList();
    }

    public void checkBooksList(){
        System.out.println("Gostaria de olhar a lista de livros cadastrados? sim/nao");
        String answer = scanner.next();

        if(answer.equalsIgnoreCase("sim")){
            listAllBooks();
        } else {
            System.out.println("Vamos te direcionar a página de empréstimos.");
            createLoan();
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
                System.out.println("Id: " + book.getBookId());
                System.out.println("Data de registro: " + book.getDateRegisterFormatted());
            }
        }
        createLoan();
    }

    public Book registerBook(String title, Author author) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equals(author)) {
                System.out.println("Esse livro já está cadastrado no sistema!");
                return book;
            }
        }

        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        books.add(newBook);

        System.out.println("Você inseriu o livro " + title + " do autor/autora " + author.getName() + " na lista.");
        newBook.bookData();

        return newBook;
    }

    public void registerBookByAuthor(){
        System.out.println("Você está na página de cadastro de livros por autor.");
        System.out.println("Insira o nome do autor que você procura:");
        String name = readLineSafe();

        Author selectedAuthor = null;

        for (Author author : authors) {
            if (author.getName().equalsIgnoreCase(name)) {
                selectedAuthor = author;
                System.out.println("Autor/autora de nome " + name + " foi encontrado.");
                break;
            }
        }

        if (selectedAuthor == null) {
            System.out.println("Autor/autora não encontrado.");
            selectedAuthor = handleAuthorFlow();
        }

        System.out.println("Insira o nome do livro:");
        String bookTitle = readLineSafe();

        registerBook(bookTitle, selectedAuthor);

        registerAnotherBookByAuthor();
    }

    public void registerAnotherBookByAuthor(){
        System.out.println("Gostaria de inserir outro livro? (sim/nao)");
        String answer = scanner.nextLine();
        if(answer.equalsIgnoreCase("nao")){
            findAuthorByName();
        } else{
            registerBookByAuthor();
        }
    }

    public void findAuthorByName(){
        System.out.println("Você está na página de procura de livros através do nome do autor.");
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
                    "\n Sua data de nascimento é em " + authorBirthday.format(formatter));
            return newAuthor;
    }

    public void insertAnotherAuthor(){
        System.out.println("Gostaria de inserir outro autor? (sim/nao)");
        String answer = scanner.next();
        if(answer.equalsIgnoreCase("nao")){
            registerBookByAuthor();
        } else{
            handleAuthorFlow();
        }
        registerBookByAuthor();
    }

    public User readValidUser() {

        boolean realUser = false;

        while (!realUser) {
            System.out.println("Informe o id do usuário:");
            Long userId = scanner.nextLong();
            scanner.nextLine();

            User user = findUserById(userId);
            if (user != null) {
                realUser = true;
                return user;
            } else {
                System.out.println("Usuário não encontrado. Tente novamente.");
            }
        }

        return null;
    }

    public Book readValidBook() {

        boolean realBook = false;

        while (!realBook) {
            System.out.println("Insira o ID do livro que gostaria de emprestar:");
            Long bookId = scanner.nextLong();
            scanner.nextLine();

            Book book = findBookById(bookId);
            if (book != null) {
                realBook = true;
                return book;
            } else {
                System.out.println("Livro não encontrado. Tente novamente.");
            }
        }
        return null;
    }


    private User findUserById(Long userId){
        for(User user : users){
            if(user.getUserId().equals(userId)){
                System.out.println("Usuário de id " + userId + " encontrado.");
                return user;
            }
        }
        return null;
    }

    private Book findBookById(Long bookId){
        for(Book book : books){
            if(Objects.equals(book.getBookId(), bookId)){
                System.out.println("Livro de número " + book.bookId + " encontrado no sistema. Seu título é: " + book.title + " escrito por " + book.author);
                return book;
            }
        }
        return null;
    }



    public void createLoan(User user, Book book){
        Loan loan = new Loan(user, book);
        System.out.println("Empréstimo criado para " + user.getName() + " com o livro " + book.getTitle());
    }

    public void createLoan() {
        System.out.println("Você está na página de empréstimos.");

        User currentUser = readValidUser();
        Book currentBook = readValidBook();

        boolean alreadyLoan = false;
        boolean hasDebt = false;

        for (Loan loan : loans) {
            if (Objects.equals(loan.getBook().getBookId(), currentBook.getBookId()) && loan.getReturnDate() == null) {
                alreadyLoan = true;
                System.out.println("Livro indisponível no momento. Escolha outro livro.");
                break;
            }

            if (loan.getUser().getUserId().equals(currentUser.getUserId()) && loan.getReturnDate() == null) {
                hasDebt = true;
                handlePendingFees(currentUser);
                payUserFee(currentUser);
                break;
            }
        }

        if (!alreadyLoan && !hasDebt) {
            System.out.println("Livro disponível para empréstimo. Deseja confirmar? (sim/nao)");
            String answer = readLineSafe();
            if (answer.equalsIgnoreCase("sim")) {
                currentBook.setBorrowed(true);
                Loan newLoan = new Loan(currentUser, currentBook);
                newLoan.calculateDueDate();
                loans.add(newLoan);
                System.out.println("Você emprestou o livro \"" + currentBook.getTitle() + "\"" + " escrito por " + currentBook.getAuthor());
            } else {
                System.out.println("Operação cancelada. Obrigado por usar nossos serviços.");
            }
        }
    }

    public void createAnotherLoan(){
        System.out.println("Gostaria de realizar outro empréstimo? (sim/nao)");
        String answer = readLineSafe();
        if (answer.equalsIgnoreCase("sim")) {
            createLoan();
        } else{
            System.out.println("Operação cancelada. Obrigado por usar nossos serviços.");
        }
    }



    public void handlePendingFees(User user){
        List<Loan> loans = user.getLoans();
        double totalFee = 0.0;

        for (Loan loan : loans) {
            LocalDate dueDate = loan.getDueDate();
            LocalDate returnDate = loan.getReturnDate();
            long daysLate = 0;

            if (returnDate == null) {
                if (LocalDate.now().isAfter(dueDate)) {
                    daysLate = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
                }
            } else {
                if (returnDate.isAfter(dueDate)) {
                    daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
                }
            }

            totalFee += Fees.calculateFee(daysLate);
        }

        user.setPendingFees(totalFee);

        if (totalFee > 0) {
            user.setBlocked(true);
            System.out.println("Usuário " + user.getName() +
                    " possui multa de R$ " + totalFee + " e está bloqueado.");
        } else {
            user.setBlocked(false);
            System.out.println("Usuário " + user.getName() + " não possui multas pendentes.");
        }
    }

    public void payUserFee(User user) {
        user.checkFeeStatus();
        if (user.getPendingFees() > 0) {
            System.out.println("Digite o valor que deseja pagar:");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            user.payFee(amount);
        }
    }

    public void checkUserFeeStatus(User user) {
        user.checkFeeStatus();
    }

}
