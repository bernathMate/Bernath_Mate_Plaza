package com.codecool.plaza.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopImpl implements Shop{

    private String name;
    private String owner;
    private boolean isOpen;
    private Map<Long, ShopEntry> products;

    public ShopImpl(String name, String owner) {
        this.name = name;
        this.owner = owner;
        this.products = new HashMap<>();
    }

    public Map<Long, ShopEntry> getProducts() {
        return products;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOwner() {
        return this.owner;
    }

    @Override
    public boolean isOpen() {
        return this.isOpen;
    }

    @Override
    public void open() {
        this.isOpen = true;
    }

    @Override
    public void close() {
        this.isOpen = false;
    }

    @Override
    public Product findByName(String name) throws ShopIsClosedException {
        if (!this.isOpen) { throw new ShopIsClosedException("Shop is closed!"); }

        for (Long barcode: products.keySet()) {
            if (products.get(barcode).getProduct().getName().equals(name)) {
                return products.get(barcode).getProduct();
            }
        }
        return null;
    }

    @Override
    public boolean hasProduct(long barcode) throws ShopIsClosedException {
        if (!this.isOpen) { throw new ShopIsClosedException("Shop is closed!"); }

        if (products.keySet().contains(barcode)) {
            return true;
        }
        return false;
    }

    @Override
    public void addNewProduct(Product product, int quantity, float price) throws ProductAlreadyExistsException, ShopIsClosedException {
        if (!this.isOpen) { throw new ShopIsClosedException("Shop is closed!"); }

        while (true) {
            if (hasProduct(product.getBarcode())) {
                throw new ProductAlreadyExistsException("This product is already exist!");
            } else {
                ShopEntry newProduct = new ShopEntry(product, quantity, price);
                products.put(product.getBarcode(), newProduct);
                break;
            }
        }
    }

    @Override
    public void addProduct(long barcode, int quantity) throws NoSuchProductException, ShopIsClosedException {
        if (!this.isOpen) { throw new ShopIsClosedException("Shop is closed!"); }

        if (hasProduct(barcode)) {
            for (Long productBarcode: products.keySet()) {
                if (productBarcode == barcode) {
                    products.get(productBarcode).increaseQuantity(quantity);
                }
            }
        } else {
            throw new NoSuchProductException("There is no such product!");
        }
    }

    @Override
    public Product buyProduct(long barcode) throws NoSuchProductException, OutOfStockException, ShopIsClosedException {
        if (!this.isOpen) { throw new ShopIsClosedException("Shop is closed!"); }

        if (hasProduct(barcode)) {
            if (products.get(barcode).getQuantity() > 0) {
                products.get(barcode).decreaseQuantity(1);
                return products.get(barcode).getProduct();
            }
            throw new OutOfStockException("Product is out of stock!");
        }
        throw new NoSuchProductException("There is no such product!");
    }

    @Override
    public List<Product> buyProducts(long barcode, int quantity) throws NoSuchProductException, OutOfStockException, ShopIsClosedException {
        if (!this.isOpen) { throw new ShopIsClosedException("Shop is closed!"); }

        List<Product> boughtProduct = new ArrayList<>();
        if (hasProduct(barcode)) {
            if (products.get(barcode).getQuantity() > quantity) {
                products.get(barcode).decreaseQuantity(quantity);
                for (int i = 0; i < quantity; i++) {
                    boughtProduct.add(products.get(barcode).getProduct());
                }
                return boughtProduct;
            }
            throw new OutOfStockException("Product is out of stock!");
        }
        throw new NoSuchProductException("There is no such product!");
    }

    @Override
    public List<Product> getAllProduct() throws ShopIsClosedException{
        if (!this.isOpen) { throw new ShopIsClosedException("Shop is closed!"); }

        List<Product> allProduct = new ArrayList<>();
        for (Long barcode: products.keySet()) {
            allProduct.add(products.get(barcode).getProduct());
        }
        return allProduct;
    }

    @Override
    public float getPrice(long barcode) {
        return this.products.get(barcode).getPrice();
    }

    private class ShopEntry {

        private Product product;
        private int quantity;
        private float price;

        ShopEntry(Product product, int quantity, float price) {
            this.product = product;
            this.quantity = quantity;
            this.price = price;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void increaseQuantity(int amount) {
            this.quantity += amount;
        }

        public void decreaseQuantity(int amount) {
            this.quantity -= amount;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "ShopImplEntry{" +
                    "product=" + product +
                    ", quantity=" + quantity +
                    ", price=" + price +
                    '}';
        }
    }
}
