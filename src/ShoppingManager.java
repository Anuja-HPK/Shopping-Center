import java.io.IOException;

public interface ShoppingManager {
    void addNewProduct();
    void deleteProduct(String productId);
    void printProductList();
    void saveInFile() throws IOException;
    void loadFromFile() throws IOException, ClassNotFoundException;
    Product getProductById(String productId);
}
