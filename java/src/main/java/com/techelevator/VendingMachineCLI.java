package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.math.BigDecimal;
import java.util.Scanner;

public class VendingMachineCLI {

	// static constants representing menu options
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION };

	// Creating the base objects
	private Scanner userInput = new Scanner(System.in);
	private ReaderWriter readerWriter;
	private Menu menu;
	private VendingMachine vendingMachine;

	// Constructor to hold the base objects in the main method
	public VendingMachineCLI(Menu menu, VendingMachine vendingMachine, ReaderWriter readerWriter) {
		this.menu = menu;
		this.vendingMachine = vendingMachine;
		this.readerWriter = readerWriter;
	}

	public void run() {

		// switchers to handle the interface loops
		boolean menuSwitcher = true;
		boolean looper = true;

		// main loop that goes back and forth between menus until the user chooses to exit
		while (looper) {

			// first menu shown and user chooses condition
			while (menuSwitcher) {
				String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

				if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
					System.out.println(vendingMachine.getItemsInMachine()); // shows the items' properties as string
				} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
					menuSwitcher = false; // switches to hide this menu and show second menu
				} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
					// the writer log is written to an actual file, and the both switchers turn off
					readerWriter.finishLog();
					looper = false;
					menuSwitcher = false;
				}
			}

			// second menu shown and user choices condition
			while (!menuSwitcher && looper) {
				// this is the method from the first menu overloaded that passes the vending machine so it can read the money
				String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS, vendingMachine);

				if (choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
					System.out.print("How many dollars do you put in (NO COINS): ");
					String feedString = userInput.nextLine(); // getting the money to feed in

					// checks if feed was valid dollar amount and processes valid inputs
					boolean validFeed = feedString.equals("1") || feedString.equals("2") || feedString.equals("5") || feedString.equals("10") || feedString.equals("20");
					if (validFeed) {
						vendingMachine.putMoneyInMachine(BigDecimal.valueOf(Integer.parseInt(feedString)));
						menu.setVendingMachine(vendingMachine); // the menu prints out money in the machine so this updates it
						readerWriter.addToLog("FEED MONEY: ", BigDecimal.valueOf(Integer.parseInt(feedString)), vendingMachine.getMoneyInMachine());
					}
				}

				else if (choice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
					System.out.println(vendingMachine.getItemsInMachine());
					System.out.print("Enter item slot: ");
					String userSlot = (userInput.nextLine()).toUpperCase(); // getting the desired slot
					vendingMachine.processPurchase(userSlot); // prints out messages and updates stock
				}

				else if (choice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
					vendingMachine.makeChange();
					menuSwitcher = true;
				}
			}
		}
	}

	// Populating the interface with the appropriate objects then starting it
	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		ReaderWriter readerWriter = new ReaderWriter();
		VendingMachine vendingMachine = new VendingMachine(new File("vendingmachine.csv"), readerWriter);
		VendingMachineCLI cli = new VendingMachineCLI(menu, vendingMachine, readerWriter);
		cli.run();
	}
}
