package com.codecool.plaza.cmdprog;

import com.codecool.plaza.api.NoSuchShopException;
import com.codecool.plaza.api.PlazaIsClosedException;

import java.text.ParseException;

public class Main {

    private String[] args;

    public Main(String[] args) {
        this.args = args;
    }

    public static void main(String[] args) throws NoSuchShopException, PlazaIsClosedException, ParseException {
        new CmdProgram(args).run();
    }
}
