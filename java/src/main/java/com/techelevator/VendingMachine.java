package com.techelevator;

import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class VendingMachine {

    // instance variables
    private Map<String, Items> itemsInMachine;
    private BigDecimal moneyInMachine;
    private ReaderWriter readerWriter;

    // formatters
    private NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(Locale.US);

    // constructor that populates the machine with what was in the input file
    public VendingMachine(File inputfile, ReaderWriter readerWriter) {
        this.readerWriter = readerWriter;
        itemsInMachine = readerWriter.readSomething(inputfile);
        moneyInMachine = BigDecimal.valueOf(0.00);
    }

    // getter that returns items in the vending machine converted to a readable string
    public String getItemsInMachine() {
        String readableItems = "";
        for(Map.Entry<String, Items> item : itemsInMachine.entrySet()) {
            // stock instance that checks if sold out
            String stock = item.getValue().getStock() == 0 ? "SOLD OUT" : String.valueOf(item.getValue().getStock());
            // adds to the string the next item
            readableItems += item.getKey() + "  " + item.getValue().getName() + "  " + stock + "  " + dollarFormat.format(item.getValue().getPrice()) + "\n";
        }
        readableItems = readableItems.substring(0, readableItems.length()-1); // gets rid the last line break from loop
        return readableItems;
    }

    // getters
    public BigDecimal getMoneyInMachine() {
        return moneyInMachine;
    }

    // setters for money stuff
    public void putMoneyInMachine(BigDecimal input) {
        moneyInMachine = moneyInMachine.add(input);
    }
    public void subtractMoneyInMachine(BigDecimal input) {
        moneyInMachine = moneyInMachine.subtract(input);
    }
    public void emptyMoney() {
        moneyInMachine = BigDecimal.valueOf(0);
    }


    public void processPurchase(String userSlot) {
        // exception handling of invalid inputs
        try {
            BigDecimal itemPrice = itemsInMachine.get(userSlot).getPrice();

            // first condition for processing successful valid transactions
            if (moneyInMachine.compareTo(itemPrice) >= 0 && itemsInMachine.get(userSlot).getStock() > 0) {
                BigDecimal tempPrice = itemsInMachine.get(userSlot).getPrice(); // keeps instance of initial balance
                subtractMoneyInMachine(tempPrice);
                readerWriter.addToLog(itemsInMachine.get(userSlot).getName() + " " + userSlot + " ", tempPrice, getMoneyInMachine());
                itemsInMachine.get(userSlot).adjustStock();
                System.out.println(itemsInMachine.get(userSlot).getDispensingMessage());

            // secondary conditions for different errors
            } else if (getMoneyInMachine().compareTo(itemPrice) >= 0) {
                System.out.println("*** SOLD OUT ***");
            } else if (itemsInMachine.containsKey(userSlot)) {
                System.out.println("*** NOT ENOUGH FUNDS ***");
            }
        } catch (Exception e) {
            System.out.println("*** INVALID SLOT ****");
        }
    }

    // method for emptying the money and printing a string from a copy of the initial balance
    public void makeChange() {
        BigDecimal tempMoney = getMoneyInMachine();
        emptyMoney();
        readerWriter.addToLog("GIVE CHANGE: ", tempMoney, getMoneyInMachine());
        // this method uses a tempMoney to make the addToLog more readable
        int dollars = 0;
        int quarters = 0;
        int dimes = 0;
        int nickels = 0;
        while (tempMoney.compareTo(BigDecimal.valueOf(0)) > 0) {
            if (tempMoney.compareTo(BigDecimal.valueOf(1)) >= 0) {
                dollars++;
                tempMoney = tempMoney.subtract(BigDecimal.valueOf(1));
            } else if (tempMoney.compareTo(BigDecimal.valueOf(0.25)) >= 0) {
                quarters++;
                tempMoney = tempMoney.subtract(BigDecimal.valueOf(0.25));
            } else if (tempMoney.compareTo(BigDecimal.valueOf(0.1)) >= 0) {
                dimes++;
                tempMoney = tempMoney.subtract(BigDecimal.valueOf(0.1));
            } else if (tempMoney.compareTo(BigDecimal.valueOf(0.05)) >= 0) {
                nickels++;
                tempMoney = tempMoney.subtract(BigDecimal.valueOf(0.05));
            }
        }
        System.out.println("Your change is " + dollars + " dollars, " + quarters + " quarters, " + dimes + " dimes, and " + nickels + " nickels");
    }
}
