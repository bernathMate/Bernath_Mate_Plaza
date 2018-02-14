package com.codecool.plaza.api;

import java.util.ArrayList;
import java.util.List;

public class PlazaImpl implements Plaza{

    private String name;
    private List<Shop> shops;
    private boolean isOpen;

    public PlazaImpl(String name) {
        this.name = name;
        this.shops = new ArrayList<Shop>();
    }

    public String getName() {
        return name;
    }

    @Override
    public List<Shop> getShops() throws PlazaIsClosedException {
        if (!this.isOpen) { throw new PlazaIsClosedException("Plaza is closed!"); }
        return shops;
    }

    @Override
    public void addShop(Shop shop) throws ShopAlreadyExistsException, PlazaIsClosedException {
        if (!this.isOpen) { throw new PlazaIsClosedException("Plaza is closed!"); }
        if (this.shops.contains(shop)) { throw new ShopAlreadyExistsException("This shop is already exist!"); }
        shops.add(shop);
    }

    @Override
    public void removeShop(Shop shop) throws NoSuchShopException, PlazaIsClosedException {
        if (!this.isOpen) { throw new PlazaIsClosedException("Plaza is closed!"); }
        if (!this.shops.contains(shop)) { throw new NoSuchShopException("There is no such shop!"); }
        shops.remove(shop);
    }

    @Override
    public Shop findShopByName(String name) throws NoSuchShopException, PlazaIsClosedException {
        if (!this.isOpen) { throw new PlazaIsClosedException("Plaza is closed!"); }

        for (Shop shop: shops) {
            if (shop.getName().equals(name)) {
                return shop;
            }
        }

        throw new NoSuchShopException("There is no such shop!");
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
    public String toString() {
        return "PlazaImpl{" +
                "name='" + name + '\'' +
                ", shops=" + shops +
                ", isOpen=" + isOpen +
                '}';
    }
}
