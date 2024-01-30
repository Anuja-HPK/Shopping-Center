import java.io.Serializable;

public class Electronics extends Product implements Serializable {
    private String brand;
    private int warrantyPeriod;

    public Electronics(String productId, String productName, int numberOfAvailableItems, double price, String brand, int warrantyPeriod) {
        super(productId, productName, numberOfAvailableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }
 
    public String getBrand() {         // getters and setters for the variables 
        return brand;    
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    void displayProducts() {
        System.out.println("                              ");
        System.out.println("Electronic Product, ");
        System.out.println("Product ID: " + getProductId());
        System.out.println("Product Name: " + getProductName());
        System.out.println("Available Items: " + getNumberOfAvailableItems());
        System.out.println("Price: " + "$" + getPrice());
        System.out.println("Brand: " + getBrand());
        System.out.println("Warranty Period: " + getWarrantyPeriod() + " " +"months");
        System.out.println("-------------------------------");
    }

}
