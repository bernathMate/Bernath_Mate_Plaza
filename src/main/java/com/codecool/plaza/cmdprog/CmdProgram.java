package com.codecool.plaza.cmdprog;

import com.codecool.plaza.api.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CmdProgram {

    private List<Product> cart;
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
        System.out.println("Have a nice day, goodbye!");
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
            for(Long barcode: shop.getProducts().keySet()) {
                System.out.println(" - " + shop.getProducts().get(barcode).getProduct().getName());
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

    private Product createNewProduct() throws ParseException {
        Product newProduct = null;
        System.out.println("Type the new product's type: (Food or Clothing)");
        String productType = sc.nextLine();

        if (productType.toLowerCase().equals("food")) {
            System.out.println("Type the name: ");
            String name = sc.nextLine();
            System.out.println("Type the barcode: ");
            long barcode = sc.nextLong();
            System.out.println("Type the manufacturer: ");
            sc.nextLine();
            String manufacturer = sc.nextLine();
            System.out.println("Type the calories: ");
            int calories = sc.nextInt();
            System.out.println("Type the expiration date: (yyyy/MM/dd)");
            sc.nextLine();
            String dateAsString = sc.nextLine();
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
            Date date = format.parse(dateAsString);
            newProduct = new FoodProduct(name, barcode, manufacturer, calories, date);
        } else if (productType.toLowerCase().equals("clothing")) {
            System.out.println("Type the name: ");
            String name = sc.nextLine();
            System.out.println("Type the barcode: ");
            long barcode = sc.nextLong();
            System.out.println("Type the manufacturer: ");
            sc.nextLine();
            String manufacturer = sc.nextLine();
            System.out.println("Type the material: ");
            String material = sc.nextLine();
            System.out.println("Type the type: ");
            String type = sc.nextLine();
            newProduct = new ClothingProduct(name, barcode, manufacturer, material, type);
        }

        return newProduct;
    }

    private void handleAddNewProduct() throws ParseException {
        Product newProduct = createNewProduct();
        System.out.println("Type the quantity: ");
        int quantity = sc.nextInt();
        System.out.println("Type the price: ");
        float price = sc.nextFloat();

        try {
            shop.addNewProduct(newProduct, quantity, price);
        } catch (ProductAlreadyExistsException e) {
            System.out.println("This product is already exist!");
        } catch (ShopIsClosedException e) {
            System.out.println("This shop is closed!");
        }
    }

    private void handleAddProduct() {
    }

    private void handleBuyProductByBarcode() {
    }
}
