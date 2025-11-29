package group27.landRegistration.nonUsers;

import java.time.LocalDate;

public class PaymentSlip {
    private static int IDCounter = 100000;

    private int slipID, ownerID, plotID, transactionID;
    private double amount;
    private String paymentMethod, status;
    private LocalDate paymentDate;

    public PaymentSlip(int ownerID, int plotID, int transactionID, double amount, String paymentMethod, String status, LocalDate paymentDate) {
        this.slipID = IDCounter++;
        this.ownerID = ownerID;
        this.plotID = plotID;
        this.transactionID = transactionID;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.paymentDate = paymentDate;
    }


    public int getSlipID() {
        return slipID;
    }

    public void setSlipID(int slipID) {
        this.slipID = slipID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getPlotID() {
        return plotID;
    }

    public void setPlotID(int plotID) {
        this.plotID = plotID;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {return paymentMethod;}

    public void setPaymentMethod(String paymentMethod) {this.paymentMethod = paymentMethod;}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void markPaid(int transactionID){
        this.transactionID = transactionID;
        this.status = "Paid";
        this.paymentDate = LocalDate.now();
    }
    public boolean isPaid(){
        return "Paid".equalsIgnoreCase(status);
    }
}
