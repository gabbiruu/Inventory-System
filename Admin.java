import java.io.*;
import java.util.*;

public class Admin {
    static Scanner sc = new Scanner(System.in);
    private static final String INVENTORY_FILE = "inventory.txt";
    private static final String CHARACTERS = "ZACF0123456";
    private static final int ID_LENGTH = 3;

    public static String generateProductID(){
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        Random random = new Random();

        for(int i = 0; i < ID_LENGTH; i++ ){
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar).trimToSize();
        }
        return sb.toString();
    }


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
            String productId = generateProductID();

            //Switch Case
            switch (choice){
                case 'A':
                    try {
                        String productName;
                        String productQuantity;
                        double productPrice = 0;

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
                                System.out.println("Only Accepts numerical value");
                            }
                        }while(!productQuantity.matches(numbersOnly));

                        while(true){
                            System.out.print("Enter Price: ");
                            if(sc.hasNextInt()){
                                productPrice = sc.nextDouble();
                                break;
                            }else{
                                System.out.println("Only Accepts numerical values");
                                sc.next();
                            }
                        }
                        //end validation

                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(INVENTORY_FILE, true));

                        bufferedWriter.write(productId + " " + productName + " " + productQuantity + " " + productPrice +  "\n");
                        System.out.println("=============================================================");
                        bufferedWriter.close();

                    }catch(IOException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 'B':
                    String productName;
                    int addQuantity = 0;
                    String lettersOnly = "^[A-Za-z]+$";

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

                    while(true){
                        System.out.print("Add Quantity: ");
                        if(sc.hasNextInt()){
                            addQuantity = sc.nextInt();
                            break;
                        }else{
                            System.out.println("Only Accepts numerical values");
                            sc.next();
                        }
                    }
                    //End validation


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
                        if (line.startsWith(productName, 4)) {
                            // Extract the stock name and current quantity from th  e line
                            String[] parts = line.split(" ");
                            String productID = parts[0].trim();
                            String stockName = parts[1].trim();
                            int currentQuantity = Integer.parseInt(parts[2].trim());
                            double productPrice = Double.parseDouble(parts[3].trim());


                            // Update the current quantity
                            int newQuantity = currentQuantity + addQuantity;

                            // Update the line with the new quantity
                            lines.set(i,productID + " " + stockName + " " + newQuantity + " " + productPrice + "\n");
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

                            if(seperator.length == 4){ // check if the file has 3 values
                                String productID = seperator[0];
                                String stockName = seperator[1];
                                int productQuantity = Integer.parseInt(seperator[2]);
                                double productPrice = Double.parseDouble(seperator[3]);

                                System.out.println("|| ID: " + productID + " || " + "Product: " + stockName + " || " + " Stock: " + productQuantity + " | | "+  " Price: " + productPrice + "");
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
