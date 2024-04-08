# BankSys 
This is a toy project simulating an OO Bank System.

 Simulates the main Bank System transactions
* Credit
* Debit
* Transfer
* Balance
 
 ```
banksys
├── src
|   └── main
|       └── java
|           └── banksys
|               ├── account
|               |   ├── exception
|               |   |   ├── InsufficientFundsException.java
|               |   |   └── NegativeAmountException.java
|               |   ├── AbstractAccount.java
|               |   ├── OrdinaryAccount.java
|               |   ├── SavingsAccount.java
|               |   ├── SpecialAccount.java
|               |   └── TaxAccountAccount.java
|               ├── control
|               |   ├── exception
|               |   |   ├── BankTransactionException.java
|               |   |   └── IncompatibleAccountException.java
|               |   └── BankController.java
|               ├── persistence
|               |   ├── exception
|               |   |   ├── AccountCreationException.java
|               |   |   ├── AccountDeletionException.java
|               |   |   ├── AccountNotFoundException.java
|               |   |   ├── ExistingAccountException.java
|               |   |   ├── FlushException.java
|               |   |   └── PersistenceException.java
|               |   ├── AccountVector.java
|               |   └── IAccountRepository.java
|               └── view
|                   └── ATM24H.java
├── test
|   └── main
|       └── java
|           └── banksys
|               └── account
|                   └── OrdinaryAccountTest.java
├── pom.xml
└── README.md
```