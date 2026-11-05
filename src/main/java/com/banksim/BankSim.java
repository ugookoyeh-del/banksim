package com.banksim;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class BankSim {
    private static final Map<String, BankAccount> ACCOUNTS = new LinkedHashMap<>();
    private static final Scanner INPUT = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== BankSim | Banking System Simulator ===");
        boolean running = true;
        while (running) {
            System.out.println("\n1. Create account  2. Deposit  3. Withdraw  4. Check balance  5. Statement  0. Exit");
            System.out.print("Select: ");
            String choice = INPUT.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> create();
                    case "2" -> transact(true);
                    case "3" -> transact(false);
                    case "4" -> showBalance();
                    case "5" -> statement();
                    case "0" -> running = false;
                    default -> System.out.println("Choose a valid menu option.");
                }
            } catch (RuntimeException ex) {
                System.out.println("Unable to complete: " + ex.getMessage());
            }
        }
        System.out.println("Thank you for using BankSim.");
    }
    private static void create() {
        System.out.print("Account number: "); String number = INPUT.nextLine().trim();
        if (ACCOUNTS.containsKey(number)) throw new IllegalArgumentException("Account number already exists");
        System.out.print("Account holder: "); String owner = INPUT.nextLine().trim();
        System.out.print("Opening balance: "); BigDecimal opening = new BigDecimal(INPUT.nextLine().trim());
        ACCOUNTS.put(number, new BankAccount(number, owner, opening));
        System.out.println("Account created.");
    }
    private static BankAccount find() {
        System.out.print("Account number: ");
        BankAccount account = ACCOUNTS.get(INPUT.nextLine().trim());
        if (account == null) throw new IllegalArgumentException("Account not found");
        return account;
    }
    private static void transact(boolean deposit) {
        BankAccount account = find();
        System.out.print("Amount: "); BigDecimal amount = new BigDecimal(INPUT.nextLine().trim());
        if (deposit) account.deposit(amount); else account.withdraw(amount);
        System.out.println("Transaction successful. Balance: " + account.getBalance());
    }
    private static void showBalance() {
        BankAccount a = find();
        System.out.printf("%s | %s | Balance: %s%n", a.getAccountNumber(), a.getOwner(), a.getBalance());
    }
    private static void statement() {
        BankAccount a = find();
        System.out.println("Statement for " + a.getOwner());
        a.getHistory().forEach(e -> System.out.printf("%s %s | balance %s%n", e.type(), e.amount(), e.balanceAfter()));
    }
}