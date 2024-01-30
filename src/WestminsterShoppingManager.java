import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager {

    private static final int MAX_PRODUCTS = 50;
    private List<Product> products;
    private Scanner scanner;
    private ShoppingCenterGUI userGUI; 
    private static final String FILENAME = "productsList.dat";

    public List<Product> getProducts() {
        return products;
    }

    public WestminsterShoppingManager() {
        this.products = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void startShoppingSystem() {   // main menu implementation
        int choice;

        do {
            System.out.println("Who is using the Shopping System? ");
            System.out.println("1. User");
            System.out.println("2. Manager");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    userGUI = new ShoppingCenterGUI(products);
                    break;
                case 2:
                    managerConsoleMenu();
                    break;
                case 3:
                    System.out.println("Exiting Shopping System!....");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter (1-3).");
            }
        } while (choice != 3);
    }

    public void managerConsoleMenu() { // the manager menu
        int choice;  
        boolean exitConsole = false;
        do {
            System.out.println("---------------------------------");
            System.out.println("Westminster Shopping Manager Menu");
            System.out.println("---------------------------------");
            System.out.println("1. Add a new Product");
            System.out.println("2. Delete Product");
            System.out.println("3. Print Products List");
            System.out.println("4. Save Products to a File");
            System.out.println("5. Load Products from a File");
            System.out.println("6. Back to main menu");
            System.out.println("                       ");
            System.out.print("Enter your choice (1-6): ");
            System.out.println("                       ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addNewProduct();
                        break;
                    case 2:
                        System.out.print("Enter Product ID to delete: ");
                        String deleteProductId = scanner.nextLine();
                        deleteProduct(deleteProductId);
                        break;
                    case 3:
                        printProductList();
                        break;
                    case 4:
                        try {
                            saveInFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        try {
                            loadFromFile();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                        System.out.println("Going back....");
                        startShoppingSystem();
                        break;
                    default:
                        System.out.println("Please enter a valid option in range(1-6)... ");
                }
            } catch (Exception e) {
                System.out.println("Please enter a numerical value...");
                scanner.nextLine();
                exitConsole = false;
            }

        } while (!exitConsole);
    }

    @Override
    public void addNewProduct() {   // add products method
        int productTypeChoice;
        do {
            displayProductTypeMenu();
            productTypeChoice = readIntInput("Enter your choice: ");
        } while (!isValidProductTypeChoice(productTypeChoice));

        String productId = readStringInput("Product ID: ");
        String productName = readStringInput("Product name: ");
        int numberOfAvailableItems = readIntInput("Number of Items: ");
        double price = readDoubleInput("Price ($) : ");

        switch (productTypeChoice) {
            case 1:
                addElectronics(productId, productName, numberOfAvailableItems, price);
                break;
            case 2:
                addClothing(productId, productName, numberOfAvailableItems, price);
                break;
            default:
                System.out.println("Invalid product type choice. Please enter 1 or 2.");
        }
    }

    private void displayProductTypeMenu() {  // display products method
        System.out.println("Select the product type:");
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
    }

    private boolean isValidProductTypeChoice(int choice) {
        if (choice != 1 && choice != 2) {
            System.out.println("Invalid product type choice. Please enter 1 or 2.");
            return false;
        }
        return true;
    }

    private void addElectronics(String productId, String productName, int numberOfAvailableItems, double price) {
        String brand = readStringInput("Brand: ");
        int warrantyPeriod = readIntInput("Warranty period (in months): ");
        Product electronics = new Electronics(productId, productName, numberOfAvailableItems, price, brand, warrantyPeriod);
        addNewProductToList(electronics);
    }

    private void addClothing(String productId, String productName, int numberOfAvailableItems, double price) {
        String size = readStringInput("Size(S,M,L,XL): ");
        String color = readStringInput("Color: ");
        Product clothing = new Clothing(productId, productName, numberOfAvailableItems, price, size, color);
        addNewProductToList(clothing);
    }

    private String readStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private int readIntInput(String prompt) {
        int input = 0;
        boolean validInput = false;
        do {
            try {
                System.out.print(prompt);
                input = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a numerical value.");
            }
        } while (!validInput);
        return input;
    }

    private double readDoubleInput(String prompt) {
        double input = 0.0;
        boolean validInput = false;
        do {
            try {
                System.out.print(prompt);
                input = Double.parseDouble(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for price.");
            }
        } while (!validInput);
        return input;
    }

    public void addNewProductToList(Product product) {
        if (products.size() < MAX_PRODUCTS) {
            products.add(product);
            System.out.println("New product added successfully!");
        } else {
            System.out.println("Only up to 50 products can be added!");
        }
    }

    @Override
    public void deleteProduct(String productId) {    // delete products method
        Product productToRemove = getProductById(productId);
        if (productToRemove != null) {
            String productType;
            if (productToRemove instanceof Electronics) {
                productType = "Electronics";
            } else {
                productType = "Clothing";
            }
            System.out.println("The " + productType + " product with ID " + productId + " is deleted! ");
            products.remove(productToRemove);
        } else {
            System.out.println("Product Not Found!");
        }
    }

    @Override
    public void printProductList() {  // print products method
        List<Product> sortedProductsList = new ArrayList<>(products);
        Collections.sort(sortedProductsList, Comparator.comparing(Product::getProductId));

        for (Product product : sortedProductsList) {
            product.displayProducts();
        }
    }

    @Override
    public void saveInFile() throws IOException {  // save files method
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(products);
            System.out.println();
            System.out.println("Saved in file : " + FILENAME);
        } catch (IOException e) {
            System.out.println();
            System.out.println("Error saving products to a file!");
            throw e;
        }
    }

    @Override
    public void loadFromFile() throws IOException, ClassNotFoundException {  // load from products method
        try (ObjectInputStream objinput = new ObjectInputStream(new FileInputStream(FILENAME))) {
            products = (List<Product>) objinput.readObject();
            System.out.println();
            System.out.println("Loaded from File: " + FILENAME);
        } catch (IOException e) {
            System.out.println();
            System.out.println("Error loading products from the file!");
            throw e;
        } catch (ClassNotFoundException e) {
            System.out.println();
            System.out.println("Error loading products: Class not found!");
            throw e;
        }
    }

    @Override
    public Product getProductById(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }
}
