package group27.landRegistration.nonUsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable; // <--- IMPORTANT IMPORT
import java.time.LocalDate;
import java.util.List;

public class PaymentSlip implements Serializable { // <--- MUST IMPLEMENT THIS
    private static final long serialVersionUID = 1L; // Recommended for serialization
    private static int IDCounter = 1000;

    static {
        try {
            File file = new File("PaymentSlip.dat");
            if (file.exists() && file.length() > 0) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                List<PaymentSlip> slips = (List<PaymentSlip>) ois.readObject();
                ois.close();
                if (!slips.isEmpty()) {
                    IDCounter = slips.get(slips.size() - 1).getSlipID();
                }
            }
        } catch (Exception ignored) { }
    }

    private int slipID, ownerID, plotID, transactionID;
    private double amount;
    private String paymentMethod, status;
    private LocalDate paymentDate;

    public PaymentSlip(int ownerID, int plotID, int transactionID, double amount, String paymentMethod, String status, LocalDate paymentDate) {
        IDCounter++;
        this.slipID = IDCounter;
        this.ownerID = ownerID;
        this.plotID = plotID;
        this.transactionID = transactionID;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.paymentDate = paymentDate;
    }

    // Getters and Setters...
    public int getSlipID() { return slipID; }
    public void setSlipID(int slipID) { this.slipID = slipID; }
    public int getOwnerID() { return ownerID; }
    public void setOwnerID(int ownerID) { this.ownerID = ownerID; }
    public int getPlotID() { return plotID; }
    public void setPlotID(int plotID) { this.plotID = plotID; }
    public int getTransactionID() { return transactionID; }
    public void setTransactionID(int transactionID) { this.transactionID = transactionID; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    @Override
    public String toString() {
        return "PaymentSlip{" + "slipID=" + slipID + ", amount=" + amount + ", status='" + status + '\'' + '}';
    }
}