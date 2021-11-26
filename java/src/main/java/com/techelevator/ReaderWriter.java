package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReaderWriter {

    // constants matching item data with its index in the file lines
    private static final int SLOT_NUM = 0;
    private static final int NAME = 1;
    private static final int PRICE = 2;
    private static final int TYPE = 3;

    // instance variables
    private List<String> logText = new ArrayList<>();

    // formatters
    private NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(Locale.US);
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

    // method for reading the input file to see how to stock the items
    public Map<String, Items> readSomething(File inputFile) {

        Map<String, Items> itemsToStock = new HashMap<>(); // declaring what we're going to put together

        try (Scanner inputScanner = new Scanner(inputFile)) {
            // if file input was valid start a line by line stream of it
            while (inputScanner.hasNextLine()) {
                String itemLine = inputScanner.nextLine();
                String[] itemArray = itemLine.split("\\|");

                // creates the items based on subclass as it puts them in the map
                switch (itemArray[TYPE]) {
                    case "Chip":
                        itemsToStock.put(itemArray[SLOT_NUM], new Chip(itemArray[NAME], itemArray[PRICE]));
                        break;
                    case "Candy":
                        itemsToStock.put(itemArray[SLOT_NUM], new Candy(itemArray[NAME], itemArray[PRICE]));
                        break;
                    case "Drink":
                        itemsToStock.put(itemArray[SLOT_NUM], new Drink(itemArray[NAME], itemArray[PRICE]));
                        break;
                    case "Gum":
                        itemsToStock.put(itemArray[SLOT_NUM], new Gum(itemArray[NAME], itemArray[PRICE]));
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());;
        }

        return itemsToStock;
    }

    // method that adds a line into the transaction history kept as a string array instance
    public void addToLog(String action, BigDecimal moneyTracker, BigDecimal moneyAfter) {
        LocalDateTime now = LocalDateTime.now();
        logText.add(dtf.format(now) + " " + action + dollarFormat.format(moneyTracker) + " " + dollarFormat.format(moneyAfter));
    }

    // method that takes the string array instance of the transaction history and writes it to the output file
    public void finishLog() {
        File output = new File("log.txt");
        try (PrintWriter writer = new PrintWriter(output)) {
            for (String entry : logText) {
                writer.println(entry);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
