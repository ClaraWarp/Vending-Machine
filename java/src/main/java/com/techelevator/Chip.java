package com.techelevator;

public class Chip extends Items {

    public Chip(String name, String Price) {
        super(name, Price);
    }

    public String getDispensingMessage() { return "Crunch Crunch, Yum!"; }
}
