# BankSim

A Java 17 command-line banking simulator demonstrating object-oriented design, encapsulation, validation, collections and precise monetary arithmetic.

## Features
- Create accounts with unique account numbers
- Deposit and withdraw with validation
- Prevent overdrafts
- Check balances and print transaction history
- Store money as `BigDecimal` rather than floating-point values

## Run
```bash
mvn package
java -cp target/classes com.banksim.BankSim
```

## Scope
This is an in-memory educational simulator. Data resets when the program exits. It does not connect to banks, move real money, or provide production banking security.

## Next steps
- Persist accounts with JDBC and MySQL
- Add unit tests and account transfer support
- Add transaction IDs, timestamps and audit logging
