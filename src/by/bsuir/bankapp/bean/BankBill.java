package by.bsuir.bankapp.bean;

public class BankBill {
    private double moneyAmount;
    private String name;
    private Currency currency;


    public BankBill(double moneyAmount, String name, Currency currency) {
        this.moneyAmount = moneyAmount;
        this.name = name;
        this.currency = currency;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
