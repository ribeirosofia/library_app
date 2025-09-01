package library;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Bem-vindo ao Sistema da Biblioteca ===");

        while (running) {
            System.out.println("\nMenu Principal:");
            System.out.println("1 - Login / Cadastro de Usuário");
            System.out.println("2 - Cadastro de Autor");
            System.out.println("3 - Cadastro de Livro");
            System.out.println("4 - Procurar Livro por Autor");
            System.out.println("5 - Listar Usuários");
            System.out.println("6 - Listar Livros");
            System.out.println("7 - Criar Empréstimo");
            System.out.println("8 - Pagamento de Multas");
            System.out.println("9 - Sair");
            System.out.print("Escolha uma opção: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    library.handleUserLoginFlow();
                    break;

                case "2":
                    boolean addingAuthors = true;
                    while (addingAuthors) {
                        library.handleAuthorFlow();
                        System.out.println("Deseja adicionar outro autor? (sim/voltar)");
                        String ans = scanner.nextLine().trim();
                        if (ans.equalsIgnoreCase("voltar")) {
                            addingAuthors = false;
                        }
                    }
                    break;

                case "3":
                    boolean addingBooks = true;
                    while (addingBooks) {
                        library.registerBookByAuthor();
                        System.out.println("Deseja cadastrar outro livro? (sim/voltar)");
                        String ans = scanner.nextLine().trim();
                        if (ans.equalsIgnoreCase("voltar")) {
                            addingBooks = false;
                        }
                    }
                    break;

                case "4":
                    library.findAuthorByName();
                    break;

                case "5":
                    library.handleUserList();
                    break;

                case "6":
                    library.listAllBooks();
                    break;

                case "7":
                    boolean loaning = true;
                    while (loaning) {
                        library.createLoan();
                        System.out.println("Deseja realizar outro empréstimo? (sim/voltar)");
                        String ans = scanner.nextLine().trim();
                        if (ans.equalsIgnoreCase("voltar")) {
                            loaning = false;
                        }
                    }
                    break;

                case "8":
                    User currentUser = library.readValidUser();
                    library.handlePendingFees(currentUser);
                    break;

                case "9":
                    System.out.println("Obrigado por usar o sistema. Até logo!");
                    running = false;
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

}
