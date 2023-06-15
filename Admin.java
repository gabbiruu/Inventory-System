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
                        String productName;
                        String productQuantity;
                        String productPrice;

                        String lettersOnly = "^[A-Za-z]+$";
                        String numbersOnly = "^[0-9]+$";

                        //Validation
                        do{
                            System.out.print("Enter Product: ");
                            productName = sc.next();
                            if(productName.matches(lettersOnly)){
                                break;
                            }else{
                                System.out.println("Only Accepts alphabetical characters");
                            }
                        }while(!productName.matches(lettersOnly));

                        do{
                            System.out.print("Enter Quantity: ");
                            productQuantity = sc.next();
                            if(productQuantity.matches(numbersOnly)){
                                break;
                            }else{
                                System.out.println("Only Accepts numerical characters");
                            }
                        }while(!productQuantity.matches(numbersOnly));

                        do{
                            System.out.print("Enter Price: ");
                            productPrice = sc.next();
                            if(productPrice.matches(numbersOnly)){
                                break;
                            }else{
                                System.out.println("Only Accepts numerical characters");
                            }
                        }while(!productPrice.matches(numbersOnly));
                        //end validation

                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(INVENTORY_FILE, true));

                        bufferedWriter.write( productName + " " + productQuantity + " " + productPrice +  "\n");
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
                        if (line.startsWith(productName)) {
                            // Extract the stock name and current quantity from th  e line
                            String[] parts = line.split(" ");
                            String stockName = parts[0].trim();
                            int currentQuantity = Integer.parseInt(parts[1].trim());
                            double productPrice = Double.parseDouble(parts[2].trim());


                            // Update the current quantity
                            int newQuantity = currentQuantity + addQuantity;

                            // Update the line with the new quantity
                            lines.set(i,stockName + " " + newQuantity + " " + productPrice + "\n");
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
                    try(BufferedReader br = new BufferedReader(new FileReader(INVENTORY_FILE))){
                        System.out.println("||===========  E-GroceMarket Inventory System ===========||");
                        System.out.println("||                                                       ||");

                        String line;
                        while((line = br.readLine()) != null){ // check as long as the line is not null
                            String[] seperator = line.split(" ");

                            if(seperator.length == 3){ // check if the file has 3 values
                                String stockName = seperator[0];
                                int productQuantity = Integer.parseInt(seperator[1]);
                                double productPrice = Double.parseDouble(seperator[2]);

                                System.out.println("|| Product: " + stockName + " || " + " Stock: " + productQuantity + " | | "+  " Price: " + productPrice + "");
                                System.out.println("||-----------------------------------------------------");
                            }
                        }
                        System.out.println("||_________________________________________________________||");

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

