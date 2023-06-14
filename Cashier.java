import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Cashier {
    static Scanner sc = new Scanner(System.in);
    private static final String INVENTORY_FILE = "inventory.txt";
    private static final String RECEIPT_FILE = "checkout.txt";

    public void displayCashier(){
        while(true) {
            System.out.println("\n||=====================================================||");
            System.out.println("|| Welcome to E-GroceMarket! Please enter your choices ||");
            System.out.println("||=====================================================||");
            System.out.println("||                                                     ||");
            System.out.println("||     A. Cart Item                                    ||");
            System.out.println("||     B. Checkout                                     ||");
            System.out.println("||     C. Exit                                         ||");
            System.out.println("||                                                     ||");
            System.out.println("||=====================================================||");

            System.out.print("Enter your choice: ");
            char choice = sc.next().toUpperCase().charAt(0);


            switch (choice) {
                case 'A':
                    while(true){
                        List<String> lines = new ArrayList<>();
                        String validation = "0?";

                        System.out.print("Enter Product: ");
                        String productChoice = sc.next();
                        System.out.print("Enter Quantity: ");
                        int quantityChoice = sc.nextInt();

                        // Read the contents of the notepad file into a list
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
                            if (line.startsWith(productChoice)) {
                                // Extract the stock name and current quantity from th  e line
                                String[] parts = line.split(" ");
                                String stockName = parts[0].trim();
                                int currentQuantity = Integer.parseInt(parts[1].trim());
                                double productPrice = Double.parseDouble(parts[2].trim());


                                // Update the current quantity
                                int newQuantity = currentQuantity - quantityChoice;

                                // Update the line with the new quantity
                                lines.set(i,stockName + " " + newQuantity + " " + productPrice);

                                //Total price
                                double totalPrice = productPrice * quantityChoice;

                                //Write receipt file on different notepad
                                try(BufferedWriter bw = new BufferedWriter(new FileWriter(RECEIPT_FILE, true))){
                                    bw.write(productChoice + " " + quantityChoice + " " + productPrice + " " + totalPrice + "\n");
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
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

                        String convertInt = Integer.toString(quantityChoice);
                        if(productChoice.matches(validation) && convertInt.matches(validation)){
                            System.out.println("Checkout:");
                            //List item and total price
                            break;
                        }else{
                            System.out.println("");
                            continue;
                        }
                    }break;
                case 'B':
                    try (BufferedReader br = new BufferedReader(new FileReader(RECEIPT_FILE))){
                        System.out.println("===============  E-GroceMarket Checkout! ===============");

                        String line;
                        double totalPrice = 0;
                        while ((line = br.readLine()) != null) {
                            String[] values = line.split(" ");

                            if (values.length == 4) { // Assuming each line has three values: name, quantity, and price
                                String name = values[0];
                                int quantity = Integer.parseInt(values[1]);
                                double price = Double.parseDouble(values[2]);
                                double total = Double.parseDouble(values[3]);
                                totalPrice += total;


                                System.out.println("Name: " + name);
                                System.out.println("Quantity: " + quantity);
                                System.out.println("Price: " + price);
                                System.out.println("Total: " + total);
                                System.out.println("------------------");
                            }
                        }
//                        //Read All lines from the file
//                        List<String> fileLine = Files.readAllLines(Paths.get(INVENTORY_FILE));
//
//                        //Display content of the file
//                        for (String file : fileLine) {
//                            System.out.println(file);
                        System.out.println("Total: " + totalPrice);
                        System.out.println("===============================================================");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 'C':
                    Main.MainDisplay();
                    break;
            }
        }
    }
}
