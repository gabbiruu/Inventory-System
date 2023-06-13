import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
public class Admin {
    static Scanner sc = new Scanner(System.in);
    private static final String INVENTORY_FILE = "inventory.txt";


    public void displayAdmin(){

        while(true){
            // Display choices
            System.out.println("\n||=====================================================||");
            System.out.println("|| Welcome to E-GroceMarket! Please enter your choices ||");
            System.out.println("||=====================================================||");
            System.out.println("||                                                     ||");
            System.out.println("||     A. Add Item                                     ||");
            System.out.println("||     B. Add Quantity                                 ||");
            System.out.println("||     C. Display Inventory                            ||");
            System.out.println("||     D. Return                                       ||");
            System.out.println("||     E. Exit                                         ||");
            System.out.println("||                                                     ||");
            System.out.println("||=====================================================||");

            System.out.print("Enter your choice: ");
            char choice = sc.next().toUpperCase().charAt(0);
            // End choices

            //Switch Case
            switch (choice){
                case 'A':
                    try {
                        System.out.print("Enter Product: ");
                        String productName = sc.next();
                        System.out.print("Enter Quantity: ");
                        String productQuantity = sc.next();
                        System.out.print("Enter Price: ");
                        String productPrice = sc.next();


                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(INVENTORY_FILE, true));

                        bufferedWriter.write("Product: " + productName + " || " + " Price: " + productPrice + " || " + " Stock: " + productQuantity +  "\n");
                        System.out.println("=============================================================");
                        bufferedWriter.close();

                    }catch(IOException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 'B':
                    System.out.print("Enter Product: ");
                    String productName = sc.next();
                    System.out.print("Add Quantity: ");
                    int addQuantity = sc.nextInt();


                    // Read the contents of the notepad file into a list
                    List<String> lines = new ArrayList<>();
                    try(BufferedReader bufferedReader = new BufferedReader(new FileReader(INVENTORY_FILE))){
                        String line;
                        while((line = bufferedReader.readLine()) != null){
                            lines.add(line);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    //update the relevant stock information
                    for(int i = 0; i< lines.size(); i++) {
                        String line = lines.get(i);
                        if (line.startsWith("Product: " + productName)) {
                            // Extract the stock name and current quantity from th  e line
                            String[] parts = line.split(":");
                            String stockName = parts[1].trim();
                            String productPrice = parts[2].trim();
                            int currentQuantity = Integer.parseInt(parts[3].trim());




                            // Update the current quantity
                            int newQuantity = currentQuantity + addQuantity;

                            // Update the line with the new quantity
                            lines.set(i, "Product: " + stockName + ": " + productPrice + ": "+ newQuantity);
                            break; // Assuming there is only one occurrence of the stock information in the file
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

                case 'C':
                    try{
                        System.out.println("============  E-GroceMarket Inventory System ============");
                        System.out.println("==xProductx==  ==xPricex==  ==xStockx==" );
                        //Read All lines from the file
                        List<String> fileLine = Files.readAllLines(Paths.get(INVENTORY_FILE));

                        //Display content of the file
                        for(String file : fileLine){
                            System.out.println(file);
                        }

                        System.out.println("=========================================================");
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 'D':
                    Main.MainDisplay();
                    break;
                case 'E':
                    System.exit(0);
                    System.out.println("Thank you for visiting!!");
                    break;

            }

            //Switch Case
        }
    }
}

