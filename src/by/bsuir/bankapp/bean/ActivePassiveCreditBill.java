package by.bsuir.bankapp.bean;

public class ActivePassiveCreditBill {
    private CreditBill activeBill;
    private CreditBill passiveBill;
    private boolean canToClose;

    public CreditBill getActiveBill() {
        return activeBill;
    }

    public void setActiveBill(CreditBill activeBill) {
        this.activeBill = activeBill;
    }

    public CreditBill getPassiveBill() {
        return passiveBill;
    }

    public void setPassiveBill(CreditBill passiveBill) {
        this.passiveBill = passiveBill;
    }

    public boolean isCanToClose() {
        return canToClose;
    }

    public void setCanToClose(boolean canToClose) {
        this.canToClose = canToClose;
    }
}
