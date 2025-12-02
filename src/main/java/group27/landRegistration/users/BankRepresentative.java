package group27.landRegistration.users;

import java.time.LocalDate;

public class BankRepresentative extends User {

    public BankRepresentative(String name, String password, String email, long NID, long phoneNumber, LocalDate doB) {
        super(name, password, email, NID, phoneNumber, doB);
    }
    // Approve a mortgage request
//    public boolean approveMortgage(String applicationId) {
//        // TODO: Replace with real database or service logic
//        System.out.println("Mortgage approved for application ID: " + applicationId);
//        return true;
//    }
//
//    // Reconcile a transaction
//    //public boolean reconcileTransaction(String transactionId) {
//        // TODO: Replace with real reconciliation logic
//        System.out.println("Transaction reconciled for ID: " + transactionId);
//        return true;

    }
