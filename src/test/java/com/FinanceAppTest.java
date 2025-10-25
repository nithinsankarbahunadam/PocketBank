package com;

import com.pocketbank.exception.InsufficientFundsException;
import com.pocketbank.model.Account;
import com.pocketbank.service.AccountService;
import com.pocketbank.service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FinanceAppTest.java
 * <p>
 * JUnit 5 tests for basic behaviors of the PocketBank project:
 * - deposit increases balance
 * - withdraw throws InsufficientFundsException when appropriate
 * - transfer moves money between accounts
 * - monthly balances roll behavior
 * - recent transactions list keeps at most 5 items
 * - defensive copy behavior of getMonthlyBalances()
 */

public class FinanceAppTest {

    private Account alice;
    private Account bob;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        // initial balances chosen to make tests easy to follow
        alice = new Account("ACC1001", "Alice", 5000.0);
        bob = new Account("ACC1002", "Bob", 500.0);
        accountService = new AccountServiceImpl();
    }

    @Test
    void testDepositIncreasesBalance() {
        double before = alice.getBalance();
        accountService.deposit(alice, 250.0);
        assertEquals(before + 250.0, alice.getBalance(), 0.0001, "Deposit should increase balance by the deposited amount");
    }

    @Test
    void testWithdrawThrowsInsufficientFunds() {
        // try to withdraw more than current balance
        assertThrows(InsufficientFundsException.class, () -> alice.withdraw(5000.0),
                "Withdrawing more than balance should throw InsufficientFundsException");
    }

    @Test
    void testWithdrawReducesBalance() throws InsufficientFundsException {
        double before = alice.getBalance();
        alice.withdraw(200.0);
        assertEquals(before - 200.0, alice.getBalance(), 0.0001, "Withdraw should reduce the balance by the withdrawn amount");
    }

    @Test
    void testTransferBetweenAccounts() throws InsufficientFundsException {
        double aliceBefore = alice.getBalance();
        double bobBefore = bob.getBalance();

        // transfer 300 from Alice to Bob
        accountService.transfer(alice, bob, 300.0);

        assertEquals(aliceBefore - 300.0, alice.getBalance(), 0.0001, "Alice's balance should decrease by transfer amount");
        assertEquals(bobBefore + 300.0, bob.getBalance(), 0.0001, "Bob's balance should increase by transfer amount");
    }

    @Test
    void testTransferInsufficientFunds() {
        // attempt a transfer that should fail due to insufficient funds
        assertThrows(InsufficientFundsException.class, () -> accountService.transfer(alice, bob, 10_000.0),
                "Transfer with insufficient funds should throw InsufficientFundsException");
    }

    @Test
    void testRollMonthlyBalanceUpdatesArray() {
        // initial monthly balances were initialized to initial balance (1000.0)
        // change current balance and roll a couple of times and check latest values
        alice.deposit(500.0); // balance -> 1500.0
        alice.rollMonthlyBalance();

        alice.deposit(-200.0); // this will throw IllegalArgumentException normally; avoid — instead do withdraw
        try {
            alice.withdraw(100.0); // balance -> 1400.0
        } catch (InsufficientFundsException e) {
            fail("Unexpected InsufficientFundsException in test setup");
        }

        alice.rollMonthlyBalance(); // now latest stored should be 1400.0

        double[] balances = alice.getMonthlyBalances();
        assertNotNull(balances, "Monthly balances array should not be null");
        assertEquals(12, balances.length, "Monthly balances array should have length 12");
        // latest stored value is last element
        assertEquals(1400.0, balances[balances.length - 1], 0.0001, "Latest monthly balance (last element) should equal current balance at roll time");
    }

    @Test
    void testRecentTransactionsLimit() {
        // perform 7 small deposits so recent queue should cap at 5
        for (int i = 1; i <= 7; i++) {
            accountService.deposit(alice, 10.0 * i);
        }

        List<com.pocketbank.model.Transaction> recent = alice.getRecent();
        assertNotNull(recent, "Recent transactions should not be null");
        assertEquals(5, recent.size(), "Recent transactions queue should keep only last 5 transactions");

        // the most recent transaction should match the last deposit (7 * 10 = 70)
        com.pocketbank.model.Transaction last = recent.get(recent.size() - 1);
        assertEquals(70.0, last.getAmount(), 0.0001, "The last recent transaction amount should equal the last deposit amount");
    }

    @Test
    void testGetMonthlyBalancesReturnsDefensiveCopy() {
        // roll to have a known latest value
        alice.deposit(200.0); // balance -> 1200.0
        alice.rollMonthlyBalance();

        double[] copy1 = alice.getMonthlyBalances();
        // mutate the returned array
        copy1[copy1.length - 1] = -9999.0;

        double[] copy2 = alice.getMonthlyBalances();
        // verify the original stored values were not affected by the mutation
        assertNotEquals(copy1[copy1.length - 1], copy2[copy2.length - 1], "getMonthlyBalances should return a defensive copy (mutating the returned array should not change internal state)");
    }

}
