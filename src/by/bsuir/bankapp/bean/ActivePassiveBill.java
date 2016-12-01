package by.bsuir.bankapp.bean;

import java.io.Serializable;

public class ActivePassiveBill implements Serializable {
    private Bill activeBill;
    private Bill passiveBill;

    public Bill getActiveBill() {
        return activeBill;
    }

    public void setActiveBill(Bill activeBill) {
        this.activeBill = activeBill;
    }

    public Bill getPassiveBill() {
        return passiveBill;
    }

    public void setPassiveBill(Bill passiveBill) {
        this.passiveBill = passiveBill;
    }
}
