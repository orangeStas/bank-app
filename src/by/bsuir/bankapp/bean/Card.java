package by.bsuir.bankapp.bean;

import java.io.Serializable;

public class Card implements Serializable {
    private String number;
    private int pin;
    private CreditBill creditBill;
    private Client client;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public CreditBill getCreditBill() {
        return creditBill;
    }

    public void setCreditBill(CreditBill creditBill) {
        this.creditBill = creditBill;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
