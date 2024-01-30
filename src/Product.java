import java.io.Serializable;

abstract public class Product implements Serializable {
    private String productId;
    private String productName;
    private int numberOfAvailableItems;
    private double price;

    abstract void displayProducts();

    public Product(String productId, String productName, int numberOfAvailableItems, double price) {
        this.productId = productId;
        this.productName = productName;
        this.numberOfAvailableItems = numberOfAvailableItems;
        this.price = price;
    }

    public void setProductId(String productId) {    // getters and setters for the variables 
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setNumberOfAvailableItems(int numberOfAvailableItems) {
        this.numberOfAvailableItems = numberOfAvailableItems;
    }
    public void setPrice(double price){   // getters and setters for the variables 
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getNumberOfAvailableItems() {
        return numberOfAvailableItems;
    }

    public double getPrice() {
        return price;
    }

}


