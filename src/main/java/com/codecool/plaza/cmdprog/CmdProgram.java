package com.codecool.plaza.cmdprog;

import com.codecool.plaza.api.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CmdProgram {

    private List<Product> cart = new ArrayList<>();
    private String[] args;
    private Scanner sc = new Scanner(System.in);
    private PlazaImpl plaza;
    private ShopImpl shop;

    public CmdProgram(String[] args) {
        this.args = args;
    }

    public void run() throws PlazaIsClosedException, NoSuchShopException, ParseException {
        System.out.println("There are no plaza created yet!");
        System.out.println("Press\n 1) to create a new plaza.\n 2) to exit.");
        String answer = sc.nextLine();
        if (answer.equals("1")) {
            handCreatePlaza();
        } else {
            System.exit(0);
        }

        handlePlazaMenu();
    }

    private void cartProductsPrice() {
        float totalPrice = 0;

        try {
            List<Product> allProducts = shop.getAllProduct();
            for(Product product: allProducts) {
                float actualPrice = shop.getPrice(product.getBarcode());
                totalPrice += actualPrice;
            }
        } catch (ShopIsClosedException e) {
            System.out.println("This shop is closed!");
        }
        System.out.println("Total price: " + totalPrice);
    }

    private void printCartContent() {
        System.out.println("Cart content: ");
        if (cart.isEmpty()) {
            System.out.println(" (cart is empty)");
        }
        for (Product product: cart) {
            System.out.println(" - " + product.getName());
        }
    }

    private void handlePlazaMenu() throws NoSuchShopException, PlazaIsClosedException, ParseException {
        System.out.println("Welcome to the " + plaza.getName() + " plaza!");
        System.out.println("1) to list all shops." +
                "\n2) to add a new shop." +
                "\n3) to remove an existing shop." +
                "\n4) enter a shop by name." +
                "\n5) to open the plaza." +
                "\n6) to close the plaza." +
                "\n7) to check if the plaza is open or not." +
                "\nN) leave plaza.\n");
        while (true) {
            String line = sc.nextLine();
            if ("N".equals(line)) {
                break;
            } else if ("1".equals(line)) {
                handleListAllShops();
            } else if ("2".equals(line)) {
                handleAddNewShop();
            } else if ("3".equals(line)) {
                handleRemoveShop();
            } else if ("4".equals(line)) {
                handleShopMenu();
            } else if ("5".equals(line)) {
                handleOpenThePlaza();
            } else if ("6".equals(line)) {
                handleCloseThePlaza();
            } else if ("7".equals(line)) {
                hadlePlazaIsOpen();
            } else {
                System.out.println("Invalid command, please give a correct command!");
            }
        }
        printCartContent();
        cartProductsPrice();
        System.out.println("Have a nice day, goodbye!");
        System.exit(0);
    }

    private void handCreatePlaza() {
        System.out.println("First of all, please give me the plaza's name!");
        String name = sc.nextLine();
        plaza = new PlazaImpl(name);
    }

    private void handleListAllShops() {
        try {
            if (plaza.getShops().isEmpty()) {
                System.out.println("There is no shops!");
            } else {
                System.out.println("Shops: ");
                for (Shop shop : plaza.getShops()) {
                    System.out.println(" - " + shop.getName());
                }
            }
        } catch (PlazaIsClosedException e) {
            System.out.println("Plaza is closed!");
        }
    }

    private void handleAddNewShop() {
        System.out.println("Type the shop's name: ");
        String name = sc.nextLine();
        System.out.println("Type the shop's owner: ");
        String owner = sc.nextLine();
        Shop newShop = new ShopImpl(name, owner);

        try {
            plaza.addShop(newShop);
            System.out.println("Shop is added!");
        } catch (ShopAlreadyExistsException e) {
            System.out.println("This shop is already exist!");
        } catch (PlazaIsClosedException e) {
            System.out.println("Plaza is closed!");
        }
    }

    private void handleRemoveShop() {
        System.out.println("Type the shop's name: ");
        String shopName = sc.nextLine();

        try {
            plaza.removeShop(plaza.findShopByName(shopName));
            System.out.println("Shop is removed!");
        } catch (PlazaIsClosedException e) {
            System.out.println("Plaza is closed!");
        } catch (NoSuchShopException e) {
            System.out.println("There is no such shop!");
        }
    }

    private void handleOpenThePlaza() {
        plaza.open();
        System.out.println("Now, the plaza is opened!");
    }

    private void handleCloseThePlaza() {
        plaza.close();
        System.out.println("Now, the plaza is closed!");
    }

    private void hadlePlazaIsOpen() {
        System.out.println("Plaza is: " + (plaza.isOpen() ? "open" : "closed"));
    }

    private void handleShopMenu() throws PlazaIsClosedException, NoSuchShopException, ParseException {
        System.out.println("Type the shop's name: ");
        String shopName = sc.nextLine();

        try {
            plaza.findShopByName(shopName);
            shop = (ShopImpl) plaza.findShopByName(shopName);
        } catch (PlazaIsClosedException e) {
            System.out.println("Plaza is closed!");
            handlePlazaMenu();
        } catch (NoSuchShopException e) {
            System.out.println("There is no such shop!");
            handlePlazaMenu();
        }

        System.out.println("Welcome! This is the " + shop.getName() + "! Press");
        System.out.println("1) to list available products." +
                "\n2) to find products by name." +
                "\n3) to display the shop's owner." +
                "\n4) to open the shop." +
                "\n5) to close the shop." +
                "\n6) to add new product to the shop." +
                "\n7) to add existing products to the shop." +
                "\n8) to buy a product by barcode." +
                "\nN) go back to plaza.");

        while (true) {
            String line = sc.nextLine();
            if ("N".equals(line)) {
                handlePlazaMenu();
            } else if ("1".equals(line)) {
                handleListAllProducts();
            } else if ("2".equals(line)) {
                handleFindProductByName();
            } else if ("3".equals(line)) {
                handleShopOwner();
            } else if ("4".equals(line)) {
                handleOpenTheShop();
            } else if ("5".equals(line)) {
                handleCloseTheShop();
            } else if ("6".equals(line)) {
                handleAddNewProduct();
            } else if ("7".equals(line)) {
                handleAddProduct();
            } else if ("8".equals(line)) {
                handleBuyProductByBarcode();
            } else {
                System.out.println("Invalid command, please give a correct command!");
            }
        }
    }

    private void handleListAllProducts() {
        if (shop.getProducts().size() == 0) {
            System.out.println("There is no products!");
        } else {
            System.out.println("Products: ");
            try {
                for(Product product: shop.getAllProduct()) {
                    System.out.println(" - " + product.getName());
                    System.out.println("\t barcode: " + product.getBarcode());
                }
            } catch (ShopIsClosedException e) {
                System.out.println("This shop is closed!");
            }
        }
    }

    private void handleFindProductByName() {
        System.out.println("Type the product's name:");
        String productName = sc.nextLine();

        try {
            Product product = shop.findByName(productName);
            if (product == null) {
                System.out.println("There is no such product!");
                return;
            }
            System.out.println("Product details: " + product);
        } catch (ShopIsClosedException e) {
            System.out.println("Shop is closed!");
        }
    }

    private void handleShopOwner() {
        System.out.println("Shop's owner: " + shop.getOwner());
    }

    private void handleOpenTheShop() {
        shop.open();
        System.out.println("Now, the shop is opened!");
    }

    private void handleCloseTheShop() {
        shop.close();
        System.out.println("Now, the shop is closed!");
    }

    private Product createNewProduct() throws ParseException, InputMismatchException {
        Product newProduct = null;
        System.out.println("Type the new product's type: (Food or Clothing)");
        String productType = sc.nextLine();

        switch (productType.toLowerCase()) {
            case "food": {
                System.out.println("Type the name: ");
                String name = sc.nextLine();
                System.out.println("Type the barcode: ");
                String barcode = sc.nextLine();
                System.out.println("Type the manufacturer: ");
                String manufacturer = sc.nextLine();
                System.out.println("Type the calories: ");
                String calories = sc.nextLine();
                System.out.println("Type the expiration date: (yyyy/MM/dd)");
                String dateAsString = sc.nextLine();
                DateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
                Date date = format.parse(dateAsString);
                newProduct = new FoodProduct(name, Long.parseLong(barcode), manufacturer, Integer.parseInt(calories), date);
                break;
            }
            case "clothing": {
                System.out.println("Type the name: ");
                String name = sc.nextLine();
                System.out.println("Type the barcode: ");
                String barcode = sc.nextLine();
                System.out.println("Type the manufacturer: ");
                String manufacturer = sc.nextLine();
                System.out.println("Type the material: ");
                String material = sc.nextLine();
                System.out.println("Type the type: ");
                String type = sc.nextLine();
                newProduct = new ClothingProduct(name, Long.parseLong(barcode), manufacturer, material, type);
                break;
            }
            default:
                throw new InputMismatchException();
        }

        return newProduct;
    }

    private void handleAddNewProduct() throws ParseException {
        Product newProduct = null;
        try {
            newProduct = createNewProduct();
        } catch (InputMismatchException ime) {
            System.out.println("Invalid type!");
            return;
        }

        System.out.println("Type the quantity: ");
        String quantity = sc.nextLine();
        System.out.println("Type the price: ");
        String price = sc.nextLine();

        try {
            shop.addNewProduct(newProduct, Integer.parseInt(quantity), Float.parseFloat(price));
            System.out.println("New product is added!");
        } catch (ProductAlreadyExistsException e) {
            System.out.println("This product is already exist!");
        } catch (ShopIsClosedException e) {
            System.out.println("This shop is closed!");
        }
    }

    private void handleAddProduct() {
        System.out.println("Type the product's barcode: ");
        String barcode = sc.nextLine();
        System.out.println("Type the quantity: ");
        String quantity = sc.nextLine();

        try {
            shop.addProduct(Long.parseLong(barcode), Integer.parseInt(quantity));
            System.out.println("Product is added!");
        } catch (NoSuchProductException e) {
            System.out.println("There is no such product!");
        } catch (ShopIsClosedException e) {
            System.out.println("Shop is closed!");
        }
    }

    private void handleBuyProductByBarcode() {
        System.out.println("Type the product's barcode: ");
        String barcode = sc.nextLine();

        try {
            cart.add(shop.buyProduct(Long.parseLong(barcode)));
            System.out.println("This product is added to the cart!");
        } catch (NoSuchProductException e) {
            System.out.println("There is no such product!");
        } catch (ShopIsClosedException e) {
            System.out.println("Shop is closed");
        } catch (OutOfStockException e) {
            System.out.println("This product is out of stock!");
        }
    }
}
