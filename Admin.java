import java.io.*;
import java.util.*;

public class Admin {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String CASHIER_FILE = "cashiers.txt";
    private static final String INVENTORY_FILE = "inventory.txt";
    private static final Scanner sc = new Scanner(System.in);
    private String loggedInUser;

    public static void main(String[] args) {
        adminLogin();
    }

    public static void adminLogin() {
        System.out.print("Enter admin username: ");
        String enteredUsername = sc.next();
        System.out.print("Enter admin password: ");
        String enteredPassword = sc.next();

        if (enteredUsername.equals(ADMIN_USERNAME) && enteredPassword.equals(ADMIN_PASSWORD)) {
            System.out.println("Successfully logged in.");
            Admin admin = new Admin();
            admin.displayAdmin();
        } else {
            System.out.println("Incorrect username or password.");
            Main main = new Main();
            main.MainDisplay();
        }
    }

    public void displayAdmin() {
        while (true) {
            // Display choices
            System.out.println("\n||=====================================================||");
            System.out.println("|| Welcome to E-GroceMarket! Please enter your choices ||\n||                     Hello Admin                     ||");
            System.out.println("||=====================================================||");
            System.out.println("||                                                     ||");
            System.out.println("||     A. Add cashier                                  ||");
            System.out.println("||     B. Add Item                                     ||");
            System.out.println("||     C. Add Quantity                                 ||");
            System.out.println("||     D. Display Inventory                            ||");
            System.out.println("||     E. Return                                       ||");
            System.out.println("||     F. Exit                                         ||");
            System.out.println("||                                                     ||");
            System.out.println("||=====================================================||");

            System.out.print("Enter your choice: ");
            char choice = sc.next().toUpperCase().charAt(0);
            // End choices

            // Switch Case
            switch (choice) {
                case 'A':
                    addCashier();
                    break;
                case 'B':
                    addItem();
                    break;
                case 'C':
                    addQuantity();
                    break;
                case 'D':
                    displayInventory();
                    break;
                case 'E':
                    Main.MainDisplay();
                    break;
                case 'F':
                    System.out.println("Thank you for visiting!!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addCashier() {
        try {
            String cashierName;
            String cashierUsername;
            String cashierPassword;

            String lettersOnly = "^[A-Za-z]+$";

            // Validation
            do {
                System.out.print("Enter Cashier Name: ");
                cashierName = sc.next();
                if (cashierName.matches(lettersOnly)) {
                    break;
                } else {
                    System.out.println("Only accepts alphabetical characters");
                }
            } while (!cashierName.matches(lettersOnly));

            System.out.print("Enter Cashier Username: ");
            cashierUsername = sc.next();

            System.out.print("Enter Cashier Password: ");
            cashierPassword = sc.next();
            // End validation

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(CASHIER_FILE, true));

            bufferedWriter.write(cashierName + " " + cashierUsername + " " + cashierPassword + "\n");
            System.out.println("Cashier added successfully.");
            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addItem() {
        try {
            String productName;
            String productQuantity;
            String productPrice;

            String lettersOnly = "^[A-Za-z]+$";
            String numbersOnly = "^[0-9]+$";

            // Validation
            do {
                System.out.print("Enter Product: ");
                productName = sc.next();
                if (productName.matches(lettersOnly)) {
                    break;
                } else {
                    System.out.println("Only accepts alphabetical characters");
                }
            } while (!productName.matches(lettersOnly));

            do {
                System.out.print("Enter Quantity: ");
                productQuantity = sc.next();
                if (productQuantity.matches(numbersOnly)) {
                    break;
                } else {
                    System.out.println("Only accepts numerical characters");
                }
            } while (!productQuantity.matches(numbersOnly));

            do {
                System.out.print("Enter Price: ");
                productPrice = sc.next();
                if (productPrice.matches(numbersOnly)) {
                    break;
                } else {
                    System.out.println("Only accepts numerical characters");
                }
            } while (!productPrice.matches(numbersOnly));
            // End validation

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(INVENTORY_FILE, true));

            bufferedWriter.write(productName + " " + productQuantity + " " + productPrice + "\n");
            System.out.println("Product added successfully.");
            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addQuantity() {
        System.out.print("Enter Product: ");
        String productName = sc.next();
        System.out.print("Add Quantity: ");
        int addQuantity = sc.nextInt();
        // Read the contents of the inventory file into a list
        List<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(INVENTORY_FILE))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Update the relevant stock information
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith(productName)) {
                // Extract the stock name and current quantity from the line
                String[] parts = line.split(" ");
                String stockName = parts[0].trim();
                int currentQuantity = Integer.parseInt(parts[1].trim());
                double productPrice = Double.parseDouble(parts[2].trim());

                // Update the current quantity
                int newQuantity = currentQuantity + addQuantity;

                // Update the line with the new quantity
                lines.set(i, stockName + " " + newQuantity + " " + productPrice);
                break; // Assuming there is only one occurrence of the stock information in the file
            }
        }

        // Write the updated stock information back to the inventory file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Quantity added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayInventory() {
        try (BufferedReader br = new BufferedReader(new FileReader(INVENTORY_FILE))) {
            System.out.println("||===========  E-GroceMarket Inventory System ===========||");
            System.out.println("||                                                       ||");

            String line;
            while ((line = br.readLine()) != null) { // check as long as the line is not null
                String[] separator = line.split(" ");

                if (separator.length == 3) { // check if the file has 3 values
                    String stockName = separator[0];
                    int productQuantity = Integer.parseInt(separator[1]);
                    double productPrice = Double.parseDouble(separator[2]);

                    System.out.println("|| Product: " + stockName + " || " + " Stock: " + productQuantity + " | | " + " Price: " + productPrice + "");
                    System.out.println("||-----------------------------------------------------");
                }
            }
            System.out.println("||_________________________________________________________||");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
