package by.bsuir.bankapp.util;

import java.util.Random;

public final class BillsUtil {
    public static final String REVOCABLE_BILL_NUMBER = "3404";
    public static final String IRREVOCABLE_BILL_NUMBER = "3414";
    public static final String MONTHLY_BILL_NUMBER = "3212";
    public static final String ANNUITY_BILL_NUMBER = "3202";
    private static final Random random = new Random(999999);

    public static final String generateBillNumber() {

        return String.valueOf(10000000 + random.nextInt());
    }
}
