package com.techelevator;

public class Drink extends Items{

    public Drink(String name, String Price) { super(name, Price); }

    public String getDispensingMessage() { return "Glug Glug, Yum"; }
}
