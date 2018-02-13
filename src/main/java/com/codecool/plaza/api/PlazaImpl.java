package com.codecool.plaza.api;

import java.util.ArrayList;
import java.util.List;

public class PlazaImpl implements Plaza{

    private String name;
    private List<Shop> shops;
    private boolean isOpen;

    public PlazaImpl(String name, boolean isOpen) {
        this.name = name;
        this.isOpen = isOpen;
        this.shops = new ArrayList<Shop>();
    }

    @Override
    public List<Shop> getShops() throws PlazaIsClosedException {
        return shops;
    }

    @Override
    public void addShop(Shop shop) throws ShopAlreadyExistsException, PlazaIsClosedException {
        shops.add(shop);
    }

    @Override
    public void removeShop(Shop shop) throws NoSuchShopException, PlazaIsClosedException {
        shops.remove(shop);
    }

    @Override
    public Shop findShopByName(String name) throws NoSuchShopException, PlazaIsClosedException {
        for (Shop shop: shops) {
            if (shop.getName().equals(name)) {
                return shop;
            }
        }
        return null;
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
