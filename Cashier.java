import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Cashier {
    static Scanner sc = new Scanner(System.in);
    private static final String INVENTORY_FILE = "inventory.txt";

    public void displayCashier(){
        while(true) {
            System.out.println("\n||=====================================================||");
            System.out.println("|| Welcome to E-GroceMarket! Please enter your choices ||");
            System.out.println("||=====================================================||");
            System.out.println("||                                                     ||");
            System.out.println("||     A. Cart Item                                    ||");
            System.out.println("||     B. Display Inventory                            ||");
            System.out.println("||     C. Exit                                         ||");
            System.out.println("||                                                     ||");
            System.out.println("||=====================================================||");

            System.out.print("Enter your choice: ");
            char choice = sc.next().toUpperCase().charAt(0);


            switch (choice) {
                case 'A':
                    System.out.println("Enter Product: ");
                    String productChoice = sc.next();
                    System.out.println("Enter Quantity: ");
                    int quantityChoice = sc.nextInt();

//                    int convert = Integer.parseInt(productChoice);
//
//                    if (convert == 0 && quantityChoice == 0) {
//                        System.out.println("Checkout");
//
//                    }

                    // Read the contents of the notepad file into a list
                    List<String> lines = new ArrayList<>();
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(INVENTORY_FILE))) {
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            lines.add(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //update the relevant stock information
                    for (int i = 0; i < lines.size(); i++) {
                        String line = lines.get(i);
                        if (line.startsWith("Product: " + productChoice)) {
                            // Extract the stock name and current quantity from th  e line
                            String[] parts = line.split(": ");
                            String stockName = parts[1].trim();
                            int currentQuantity = Integer.parseInt(parts[2].trim());


                            // Update the current quantity
                            int newQuantity = currentQuantity - quantityChoice;

                            // Update the line with the new quantity
                            lines.set(i, "Product: " + stockName + ": " + newQuantity);
                            break;
                        }
                    }

                    // Write the updated stock information back to the notepad file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE))) {
                        for (String line : lines) {
                            writer.write(line);
                            writer.newLine();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 'B':
                    try {
                        System.out.println("===============  E-GroceMarket Inventory System ===============");
                        //Read All lines from the file
                        List<String> fileLine = Files.readAllLines(Paths.get(INVENTORY_FILE));

                        //Display content of the file
                        for (String file : fileLine) {
                            System.out.println(file);
                        }
                        System.out.println("===============================================================");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
