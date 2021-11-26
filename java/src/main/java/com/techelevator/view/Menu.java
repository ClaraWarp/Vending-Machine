package com.techelevator.view;

import com.techelevator.VendingMachine;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Menu {

	// creating instance variables
	private PrintWriter out;
	private Scanner in;
	private VendingMachine vendingMachine;
	private boolean displayCurMoney = false; // switcher for showing currency in vending machine

	private NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(Locale.US);

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	// method for first menu that prints and pulls from nested methods
	public Object getChoiceFromOptions(Object[] options) {
		displayCurMoney = false;
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	// above method overloaded so second menu can access vending machine's money
	public Object getChoiceFromOptions(Object[] options, VendingMachine vendingMachine) {
		displayCurMoney = true;
		this.vendingMachine = vendingMachine;
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	// get the choice from the user
	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		}
		return choice;
	}

	// displays the choices and possibly the current money
	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		if (displayCurMoney) {
			out.println(System.lineSeparator() + "Current Money Provided: " + dollarFormat.format(vendingMachine.getMoneyInMachine()));
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}

	public void setVendingMachine(VendingMachine vendingMachine) {
		this.vendingMachine = vendingMachine;
	}

}
