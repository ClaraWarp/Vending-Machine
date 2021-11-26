package com.techelevator;

public class Candy extends Items {

    public Candy(String name, String Price) {
        super(name, Price);
    }

    public String getDispensingMessage() {
        return "Munch Munch, Yum!";
    }
}
