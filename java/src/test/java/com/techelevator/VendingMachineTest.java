package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;

public class VendingMachineTest {

    // Was not able to fill out unit tests for this capstone given time restraint, but here is an example test.

    @Test
    public void put_Money_In_Machine_works_with_valid_data_should_return_expected_results() {
        ReaderWriter readerWriter = new ReaderWriter();
        VendingMachine vendingMachine = new VendingMachine(new File("vendingmachine.csv"), readerWriter);

        vendingMachine.putMoneyInMachine(BigDecimal.valueOf(5));

        BigDecimal expected = BigDecimal.valueOf(5.0);

        BigDecimal result = vendingMachine.getMoneyInMachine();

        Assert.assertEquals("Broken money feeder", expected, result);
    }

}
