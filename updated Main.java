import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        MainDisplay();
    }

    public static void MainDisplay() {
        char choice;
        System.out.println("||==============================||");
        System.out.println("||        E-GroceMarket!        ||");
        System.out.println("||==============================||");
        System.out.println("||                              ||");
        System.out.println("||                              ||");
        System.out.println("||        A. Admin              ||");
        System.out.println("||        B. Cashier            ||");
        System.out.println("||        C. Exit               ||");
        System.out.println("||                              ||");
        System.out.println("||                              ||");
        System.out.println("||==============================||");
        System.out.print("Enter your choice: ");
        choice = sc.next().toUpperCase().charAt(0);

        switch (choice) {
            case 'A' -> {
                Admin admin = new Admin();
                admin.adminLogin();
            }
            case 'B' -> {
                Cashier cashier = new Cashier();
                cashier.cashierLogin();
            }
            case 'C' -> {
                System.out.println("");
                System.out.println("Goodbye and thank you.");
                System.exit(0); // Terminate the program
            }
            default -> {
                System.out.println("Invalid choice. Please try again.");
                MainDisplay(); // Recursively call MainDisplay() for a valid choice
            }
        }
    }
}

