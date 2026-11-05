package com.banksim;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BankAccount {
    public record Entry(String type, BigDecimal amount, BigDecimal balanceAfter) {}
    private final String accountNumber;
    private final String owner;
    private BigDecimal balance;
    private final List<Entry> history = new ArrayList<>();

    public BankAccount(String accountNumber, String owner, BigDecimal openingBalance) {
        if (accountNumber == null || accountNumber.isBlank() || owner == null || owner.isBlank())
            throw new IllegalArgumentException("Account number and owner are required");
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = money(openingBalance);
        if (this.balance.signum() < 0) throw new IllegalArgumentException("Opening balance cannot be negative");
    }
    private static BigDecimal money(BigDecimal value) {
        if (value == null) throw new IllegalArgumentException("Amount is required");
        return value.setScale(2, RoundingMode.HALF_UP);
    }
    public void deposit(BigDecimal amount) {
        BigDecimal value = money(amount);
        if (value.signum() <= 0) throw new IllegalArgumentException("Deposit must be greater than zero");
        balance = balance.add(value);
        history.add(new Entry("DEPOSIT", value, balance));
    }
    public void withdraw(BigDecimal amount) {
        BigDecimal value = money(amount);
        if (value.signum() <= 0) throw new IllegalArgumentException("Withdrawal must be greater than zero");
        if (balance.compareTo(value) < 0) throw new IllegalStateException("Insufficient funds");
        balance = balance.subtract(value);
        history.add(new Entry("WITHDRAWAL", value, balance));
    }
    public String getAccountNumber() { return accountNumber; }
    public String getOwner() { return owner; }
    public BigDecimal getBalance() { return balance; }
    public List<Entry> getHistory() { return Collections.unmodifiableList(history); }
}