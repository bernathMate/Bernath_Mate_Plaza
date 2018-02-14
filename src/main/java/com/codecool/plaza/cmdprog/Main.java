package com.codecool.plaza.cmdprog;

public class Main {

    private String[] args;

    public Main(String[] args) {
        this.args = args;
    }

    public static void main(String[] args) {
        new CmdProgram(args).run();
    }
}
