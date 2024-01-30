import java.io.Serializable;

public class Clothing extends Product implements Serializable {
    private String size;
    private String color;

    public Clothing(String productId, String productName, int numberOfAvailableItems, double price, String size, String color) {
        super(productId, productName, numberOfAvailableItems, price);
        this.size = size;
        this.color = color;
    }

    public String getSize() { // getters and setters for the variables 
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    void displayProducts() {
        System.out.println("                              ");
        System.out.println("Clothing Product, ");
        System.out.println("Product ID: " + getProductId());
        System.out.println("Product Name: " + getProductName());
        System.out.println("Available Items: " + getNumberOfAvailableItems());
        System.out.println("Price: $" + getPrice());
        System.out.println("Size: " + getSize());
        System.out.println("Color: " + getColor());
        System.out.println("-------------------------------");
        
    }
}
