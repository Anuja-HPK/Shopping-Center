import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

class ShoppingCenterGUI extends JFrame {

    private JButton viewCartButton;
    private JComboBox<String> productCategoryComboBox;
    private JTable productsTable;
    private DefaultTableModel tableModel;
    private List<Product> productsList;
    private JTextArea selectedProductInfo;
    private JButton addToCartButton;

    private JTable shoppingCartTable;
    private DefaultTableModel shoppingCartTableModel;
    private JTextArea totalTextArea;

    static class HorizontalLinePanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int lineWidth = 2;
            ((Graphics2D) g).setStroke(new BasicStroke(lineWidth));

            int centerY = getHeight() / 2;

            g.drawLine(0, centerY, getWidth(), centerY);
        }
    }

    public ShoppingCenterGUI(List<Product> products) {
        this.productsList = products;
        initializeComponents();
        layoutSetup();
    }

    private void initializeComponents() { // intitializing components for products frame
        setTitle("Westminster Shopping Centre");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        productCategoryComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        productCategoryComboBox.setPreferredSize(new Dimension(150, 30));

        productsTable = new JTable();

        viewCartButton = new JButton("View Shopping Cart");
        addToCartButton = new JButton("Add to Shopping Cart");

        tableModel = new DefaultTableModel(new Object[]{"Product ID", "Name", "Category", "Price $ ", "Details"}, 0);
        productsTable.setModel(tableModel);

        selectedProductInfo = new JTextArea();
        selectedProductInfo.setLineWrap(true);
        selectedProductInfo.setColumns(30);
        selectedProductInfo.setRows(12);

        shoppingCartTableModel = new DefaultTableModel(new Object[]{"Product", "Quantity", "Price $"}, 0);
        shoppingCartTable = new JTable(shoppingCartTableModel);

        totalTextArea = new JTextArea();
        totalTextArea.setEditable(false);

        shoppingCartTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = shoppingCartTable.getSelectedRow();
            
        });

        addToCartButton.addActionListener(e -> addToShoppingCart());

        
    }

    private void layoutSetup() { // building the layout for the product frame
        JPanel topPanel = new JPanel(new GridLayout(1, 0));

        JPanel topPanel_1 = new JPanel(new GridLayout(2, 0));
        JPanel topPanel_2 = new JPanel(new GridLayout(3, 0));
        JPanel topPanel_3 = new JPanel(new GridLayout(1, 0));

        JPanel topPanel_21 = new JPanel();
        JPanel topPanel_22 = new JPanel();
        JPanel topPanel_23 = new JPanel();

        JPanel topPanel_31 = new JPanel();
        JPanel topPanel_32 = new JPanel();

        topPanel_22.add(new JLabel("Select Product Category "));
        topPanel_22.add(productCategoryComboBox);
        topPanel_32.add(viewCartButton);

        topPanel_2.add(topPanel_21);
        topPanel_2.add(topPanel_22);
        topPanel_2.add(topPanel_23);

        topPanel_3.add(topPanel_31);
        topPanel_3.add(topPanel_32);

        topPanel.add(topPanel_1);
        topPanel.add(topPanel_2);
        topPanel.add(topPanel_3);

        add(topPanel, BorderLayout.NORTH);
        topPanel.setPreferredSize(new Dimension(getWidth(), 100));

        JPanel midPanel = new JPanel(new GridLayout(3, 0));

        JPanel section1 = new JPanel(new BorderLayout());
        JPanel section2 = new JPanel(new GridLayout(9, 0));
        section2.setPreferredSize(new Dimension(900, section2.getPreferredSize().height));

        midPanel.add(section1);
        midPanel.add(new HorizontalLinePanel());
        midPanel.add(section2);

        JScrollPane scrollPane = new JScrollPane(productsTable);
        section1.add(new JPanel(), BorderLayout.NORTH);
        section1.add(scrollPane, BorderLayout.CENTER);
        productsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        int tableMargin = (getWidth() - 940) / 2;
        section1.add(Box.createHorizontalStrut(tableMargin), BorderLayout.WEST);
        section1.add(Box.createHorizontalStrut(tableMargin), BorderLayout.EAST);

        int labelMargin = (getWidth() - 800) / 2;
        section2.add(Box.createHorizontalStrut(labelMargin), BorderLayout.WEST);
        section2.add(Box.createHorizontalStrut(labelMargin), BorderLayout.EAST);

        JLabel sp = new JLabel("selected product-Details" );
        JLabel pID = new JLabel("Product ID: ");
        JLabel category = new JLabel("Category: ");
        JLabel name = new JLabel("Name: ");
        JLabel size = new JLabel("Size: ");
        JLabel colour = new JLabel("Colour: ");
        JLabel itemsAvailable = new JLabel("Items Available: ");

        section2.add(sp, BorderLayout.CENTER);
        section2.add(pID, BorderLayout.CENTER);
        section2.add(category, BorderLayout.CENTER);
        section2.add(name, BorderLayout.CENTER);
        section2.add(size, BorderLayout.CENTER);
        section2.add(colour, BorderLayout.CENTER);
        section2.add(itemsAvailable, BorderLayout.CENTER);

        add(midPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 0));

        JPanel bottomPanel1 = new JPanel();
        JPanel bottomPanel2 = new JPanel();
        bottomPanel2.add(addToCartButton);
        JPanel bottomPanel3 = new JPanel();

        bottomPanel.add(bottomPanel1, BorderLayout.SOUTH);
        bottomPanel.add(bottomPanel2, BorderLayout.SOUTH);
        bottomPanel.add(bottomPanel3, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        viewCartButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new ShoppingCartFrame(shoppingCartTableModel, totalTextArea));
        });

        productCategoryComboBox.addActionListener(e -> {
            String selectedCategory = (String) productCategoryComboBox.getSelectedItem();
            updateProductsTable(selectedCategory);
        });

        setVisible(true);
    }

    private void updateProductsTable(String selectedCategory) {
        tableModel.setRowCount(0);

        List<Product> filteredProducts = filterProductsByCategory(selectedCategory);

        for (Product product : filteredProducts) {
            addNewProductToTableModel(product);
        }
    }

    private List<Product> filterProductsByCategory(String selectedCategory) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : productsList) {
            if (selectedCategory.equals("All") ||
                    (product instanceof Electronics && selectedCategory.equals("Electronics")) ||
                    (product instanceof Clothing && selectedCategory.equals("Clothing"))) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    private void addNewProductToTableModel(Product product) { // getting ta details of products
        Object[] rowData;

        if (product instanceof Electronics) {
            Electronics electronicsProduct = (Electronics) product;
            String brandAndWarrantyPeriod = electronicsProduct.getBrand() + ", " + electronicsProduct.getWarrantyPeriod() + " months";
            rowData = new Object[]{
                    product.getProductId(),
                    product.getProductName(),
                    "Electronics",
                    product.getPrice(),
                    brandAndWarrantyPeriod,
                    "More Info"
            };
        } else {
            Clothing clothingProduct = (Clothing) product;
            String sizeAndColor = clothingProduct.getSize() + ", " + clothingProduct.getColor();
            rowData = new Object[]{
                    product.getProductId(),
                    product.getProductName(),
                    "Clothing",
                    product.getPrice(),
                    sizeAndColor,
                    "More Info"
            };
        }

        tableModel.addRow(rowData);
    }

    private void displayProductDetails(Product product) {
        selectedProductInfo.setText("Product ID: " + product.getProductId() + "\n" +
                "Product Name: " + product.getProductName() + "\n" +
                "Category: " + getProductCategory(product) + "\n" +
                "Price: " + product.getPrice() + "\n" +
                getProductInfo(product) + "\n");
    }

    private String getProductCategory(Product product) {
        if (product instanceof Electronics) {
            return "Electronics";
        } else if (product instanceof Clothing) {
            return "Clothing";
        }
        return "Unknown";
    }

    private String getProductInfo(Product product) {
        if (product instanceof Electronics) {
            Electronics electronicsProduct = (Electronics) product;
            return "Brand: " + electronicsProduct.getBrand() + "\nWarranty Period: " + electronicsProduct.getWarrantyPeriod() + " months";
        } else if (product instanceof Clothing) {
            Clothing clothingProduct = (Clothing) product;
            return "Size: " + clothingProduct.getSize() + "\nColor: " + clothingProduct.getColor();
        }
        return "No additional information available.";
    }

    private void addToShoppingCart() {  
        int selectedRow = productsTable.getSelectedRow();
        if (selectedRow != -1) {
            Product selectedProduct = productsList.get(selectedRow);
            String productName = selectedProduct.getProductName();
            String category = getProductCategory(selectedProduct);
            double price = selectedProduct.getPrice();

            // Check if the product is already in the shopping cart
            boolean productExists = false;
            for (int i = 0; i < shoppingCartTableModel.getRowCount(); i++) {
                String cartProductName = (String) shoppingCartTableModel.getValueAt(i, 0);
                if (productName.equals(cartProductName)) {
                    // Product already in the cart, increase the quantity
                    int currentQuantity = (int) shoppingCartTableModel.getValueAt(i, 1);
                    shoppingCartTableModel.setValueAt(currentQuantity + 1, i, 1);
                    productExists = true;
                    break;
                }
            }

            if (!productExists) {
                // Product is not in the cart, add it with quantity 1
                shoppingCartTableModel.addRow(new Object[]{productName, 1, price});
            }

            // Update the total value
            updateTotalValue();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product from the table.", "No Product Selected", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateTotalValue() {
        double totalValue = 0.0;
        for (int i = 0; i < shoppingCartTableModel.getRowCount(); i++) {
            int quantity = (int) shoppingCartTableModel.getValueAt(i, 1);
            double price = (double) shoppingCartTableModel.getValueAt(i, 2);
            totalValue += (quantity * price);
        }

        totalTextArea.setText("Total: $" + totalValue);
    }

}


class ShoppingCartFrame extends JFrame {

    private JTable shoppingCartTable;
    private DefaultTableModel shoppingCartTableModel;
    private JTextArea totalTextArea;

    public ShoppingCartFrame(DefaultTableModel shoppingCartTableModel, JTextArea totalTextArea) {
        this.shoppingCartTableModel = shoppingCartTableModel;
        this.totalTextArea = totalTextArea;
        initializeComponents();
        layoutSetup();
    }

    private void initializeComponents() { // components for shoping cart frame
        setTitle("Shopping Cart");
        setSize(800, 600);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        shoppingCartTable = new JTable(shoppingCartTableModel);
        JScrollPane scrollPane = new JScrollPane(shoppingCartTable);

        totalTextArea.setEditable(false);

        add(scrollPane, BorderLayout.CENTER);
        add(totalTextArea, BorderLayout.SOUTH);
    }

    private void layoutSetup() {  // layout for the shopping cart frame
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel midPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel tablePanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(shoppingCartTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.setPreferredSize(new Dimension(600, 300));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        topPanel.add(new JLabel("                     "));
        midPanel.add(tablePanel, BorderLayout.CENTER);

        int textMargin = (getWidth() - 600) / 2;
        JPanel textAreaPanel = new JPanel(new BorderLayout());
        textAreaPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        bottomPanel.add(Box.createVerticalStrut(textMargin), BorderLayout.NORTH);
        bottomPanel.add(totalTextArea, BorderLayout.CENTER);
        bottomPanel.add(Box.createVerticalStrut(textMargin), BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(midPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
