package by.bsuir.bankapp.util;

import by.bsuir.bankapp.bean.BankBill;
import by.bsuir.bankapp.bean.Currency;

import java.util.ArrayList;
import java.util.List;

public class BankBillsCreator {
    public static BankBill usdBankBill = new BankBill(1000000.0, "BILL", Currency.USD);
    public static BankBill usdCashBox = new BankBill(0.0, "CASH-BOX", Currency.USD);
    public static BankBill bynBankBill = new BankBill(1000000.0, "BILL", Currency.BYN);
    public static BankBill bynCashBox = new BankBill(0.0, "CASH-BOX", Currency.BYN);

    public static List<BankBill> getBankBills() {
        List<BankBill> bankBills = new ArrayList<>();
        bankBills.add(usdBankBill);
        bankBills.add(usdCashBox);
        bankBills.add(bynBankBill);
        bankBills.add(bynCashBox);
        return bankBills;
    }
}
