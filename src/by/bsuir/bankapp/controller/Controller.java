package by.bsuir.bankapp.controller;

import by.bsuir.bankapp.bean.*;
import by.bsuir.bankapp.dao.*;
import by.bsuir.bankapp.util.BankBillsCreator;
import by.bsuir.bankapp.util.BankDateUtil;
import by.bsuir.bankapp.util.BillsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@MultipartConfig
public class Controller extends HttpServlet {

    private ClientDao clientDao = ClientDao.getInstance();
    private DepositDao depositDao = DepositDao.getInstance();
    private BillDao billDao = BillDao.getInstace();
    private CreditDao creditDao = CreditDao.getInstance();
    private CreditBillDao creditBillDao = CreditBillDao.getInstance();
    private CardDao cardDao = CardDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = null;
        String command = req.getParameter("command");

        if (command != null) {
            switch (command) {
                case "allClients": {

                    HttpSession session = req.getSession(true);
                    if (session != null) {
                        session.removeAttribute("card");
                    }

                    List<Client> clients = clientDao.readAll();
                    req.setAttribute("clients", clients);

                    List<Deposit> deposits = depositDao.readAll();
                    req.setAttribute("deposits", deposits);

                    List<Credit> credits = creditDao.readAll();
                    req.setAttribute("credits", credits);

                    List<ActivePassiveBill> activePassiveBills = new ArrayList<>();
                    List<Bill> bills = billDao.readAll();
                    for (Bill bill : bills) {
                        bill.setCreator(clientDao.read(bill.getCreator().getIdClient()));
                        bill.setDeposit(depositDao.read(bill.getDeposit().getId()));

                        ActivePassiveBill activePassiveBill = new ActivePassiveBill();
                        if (bill.getNumber().endsWith("1")) {
                            String startNumber = bill.getNumber().substring(0, bill.getNumber().length() - 1);
                            Bill passiveBill = null;
                            for (Bill bill1 : bills) {
                                if (bill1.getNumber().startsWith(startNumber) && bill1.getNumber().endsWith("0")) {
                                    passiveBill = bill1;
                                    break;
                                }
                            }

                            activePassiveBill.setActiveBill(bill);
                            activePassiveBill.setPassiveBill(passiveBill);
                            activePassiveBills.add(activePassiveBill);
                        }
                    }

                    req.setAttribute("bills", activePassiveBills);

                    List<ActivePassiveCreditBill> activePassiveCreditBills = new ArrayList<>();
                    List<CreditBill> creditBills = creditBillDao.readAll();
                    for (CreditBill creditBill : creditBills) {
                        creditBill.setCreator(clientDao.read(creditBill.getCreator().getIdClient()));
                        creditBill.setCredit(creditDao.read(creditBill.getCredit().getId()));

                        ActivePassiveCreditBill activePassiveCreditBill = new ActivePassiveCreditBill();
                        if (creditBill.getNumber().endsWith("1")) {
                            String startNumber = creditBill.getNumber().substring(0, creditBill.getNumber().length() - 1);
                            CreditBill passiveBill = null;
                            for (CreditBill creditBill1 : creditBills) {
                                if (creditBill1.getNumber().startsWith(startNumber) && creditBill1.getNumber().endsWith("0")) {
                                    passiveBill = creditBill1;
                                    break;
                                }
                            }

                            activePassiveCreditBill.setActiveBill(creditBill);
                            activePassiveCreditBill.setPassiveBill(passiveBill);

                            switch (creditBill.getCredit().getType()) {
                                case MONTHLY: {
                                    if (creditBill.getMoneySum() == passiveBill.getMoneySum() * creditBill.getCredit().getPercent()/100.0) {
                                        activePassiveCreditBill.setCanToClose(true);
                                    }
                                    break;
                                }
                                case ANNUITY: {
                                    if (creditBill.getMoneySum() == passiveBill.getMoneySum() +
                                            passiveBill.getMoneySum()*creditBill.getCredit().getPercent() / 100.0) {
                                        activePassiveCreditBill.setCanToClose(true);
                                    }
                                }
                            }

                            activePassiveCreditBills.add(activePassiveCreditBill);
                        }
                    }

                    req.setAttribute("creditBills", activePassiveCreditBills);

                    req.setAttribute("bankBills", BankBillsCreator.getBankBills());
                    page = "/index.jsp";
                    break;
                }
                case "getClient": {
                    String passportNumber = (req.getParameter("passportNumber"));
                    Client client = clientDao.readByPassportNumber(passportNumber);
                    req.setAttribute("client", client);
                    page = "/view_client.jsp";
                    break;
                }
                case "openForEdit": {
                    String passportNumber = (req.getParameter("passportNumber"));
                    if (passportNumber != null) {
                        Client client = clientDao.readByPassportNumber(passportNumber);
                        req.setAttribute("client", client);
                    }
                    page = "/edit_client.jsp";
                    break;
                }
                case "removeClient": {
                    String passportNumber = req.getParameter("passportNumber");
                    clientDao.deleteByPassportNumber(passportNumber);
                    page = "/controller?command=allClients";
                    break;
                }

                case "chooseUserForDeposit" : {
                    String passportNumber = req.getParameter("passportNumber");
                    Client client = clientDao.readByPassportNumber(passportNumber);
                    List<Deposit> deposits = depositDao.readAll();

                    req.setAttribute("client", client);
                    req.setAttribute("deposits", deposits);

                    page = "/create_bill.jsp";
                    break;
                }

                case "chooseUserForCredit" : {
                    String passportNumber = req.getParameter("passportNumber");
                    Client client = clientDao.readByPassportNumber(passportNumber);
                    List<Credit> credits = creditDao.readAll();

                    req.setAttribute("client", client);
                    req.setAttribute("credits", credits);

                    page = "/create_credit.jsp";
                    break;
                }

                case "revokeBill" : {
                    int billActiveId = Integer.parseInt(req.getParameter("billActiveId"));
                    int billPassiveId = Integer.parseInt(req.getParameter("billPassiveId"));
                    Bill billActive = billDao.read(billActiveId);
                    Bill billPassive = billDao.read(billPassiveId);
                    if (billActive != null) {
                        billActive.setDeposit(depositDao.read(billActive.getDeposit().getId()));
                        switch (billActive.getDeposit().getCurrency()) {
                            case USD: {

                                BankBillsCreator.usdBankBill.setMoneyAmount(BankBillsCreator.usdBankBill.getMoneyAmount() - billActive.getMoneySum()
                                - billPassive.getMoneySum());
                                break;
                            }
                            case BYN: {
                                BankBillsCreator.bynBankBill.setMoneyAmount(BankBillsCreator.bynBankBill.getMoneyAmount() - billActive.getMoneySum()
                                - billPassive.getMoneySum());
                                break;
                            }
                        }

                        billDao.delete(billActive.getId());
                        billDao.delete(billPassive.getId());
                    }
                    page = "/controller?command=allClients";
                    break;
                }
                case "endBankDay" : {
                    List<Bill> bills = billDao.readAll();
                    for (Bill bill : bills) {
                        if (bill.getNumber().endsWith("1")) {
                            String startNumber = bill.getNumber().substring(0, bill.getNumber().length()-1);
                            Bill passiveBill = null;
                            for (Bill bill1 : bills) {
                                if (bill1.getNumber().startsWith(startNumber) && bill1.getNumber().endsWith("0")) {
                                    passiveBill = bill1;
                                    break;
                                }
                            }
                            if (passiveBill != null) {
                                passiveBill.setDeposit(depositDao.read(passiveBill.getDeposit().getId()));
                                Double percent = passiveBill.getDeposit().getPercent();
                                Double currentMoney = bill.getMoneySum() + passiveBill.getMoneySum();
                                Double moneyToAdd = currentMoney * percent / 100.0 / 365.0;
                                bill.setMoneySum(bill.getMoneySum() + moneyToAdd);
                                billDao.update(bill);
                            }
                        }
                        /*bill.setCredit(depositDao.read(bill.getCredit().getId()));
                        Double percent = bill.getCredit().getPercent();
                        Double currentMoney = bill.getMoneySum();
                        Double moneyToAdd = currentMoney * percent / 100.0 / 365.0;
                        bill.setMoneySum(currentMoney + moneyToAdd);
                        billDao.update(bill);

                        switch (bill.getCredit().getCurrency()) {
                            case USD: {
                                BankBillsCreator.usdBankBill.setMoneyAmount(BankBillsCreator.usdBankBill.getMoneyAmount() - moneyToAdd);
                                break;
                            }
                            case BYN: {
                                BankBillsCreator.bynBankBill.setMoneyAmount(BankBillsCreator.bynBankBill.getMoneyAmount() - moneyToAdd);
                                break;
                            }
                        }*/
                    }

                    page = "/controller?command=allClients";
                    break;
                }

                case "creditEndPeriod" : {
                    int creditBillActiveId = Integer.parseInt(req.getParameter("creditBillActiveId"));
                    int creditBillPassiveId = Integer.parseInt(req.getParameter("creditBillPassiveId"));
                    CreditBill creditActiveBill = creditBillDao.read(creditBillActiveId);
                    CreditBill creditPassiveBill = creditBillDao.read(creditBillPassiveId);

                    creditPassiveBill.setCredit(creditDao.read(creditPassiveBill.getCredit().getId()));

                    switch (creditPassiveBill.getCredit().getType()) {
                        case ANNUITY : {
                            double totalSum = creditPassiveBill.getMoneySum() +
                                    creditPassiveBill.getMoneySum() * creditPassiveBill.getCredit().getPercent() / 100.0;
                            int countMonths = BankDateUtil.getCountMonthBetweenTwoDates(creditPassiveBill.getStartDate(),
                                    creditPassiveBill.getEndDate());

                            double moneyToAdd = totalSum / (countMonths * 1.0);

                            creditActiveBill.setMoneySum(creditActiveBill.getMoneySum() + moneyToAdd);
                            creditBillDao.update(creditActiveBill);
                            break;
                        }
                        case MONTHLY : {
                            double moneyToAdd = creditPassiveBill.getMoneySum() * creditPassiveBill.getCredit().getPercent() / 100.0 / 12.0;
                            creditActiveBill.setMoneySum(creditActiveBill.getMoneySum() + moneyToAdd);
                            creditBillDao.update(creditActiveBill);
                        }
                    }

                    page = "/controller?command=allClients";
                    break;
                }

                case "closeCreditBill" : {
                    int creditBillActiveId = Integer.parseInt(req.getParameter("creditBillActiveId"));
                    int creditBillPassiveId = Integer.parseInt(req.getParameter("creditBillPassiveId"));
                    CreditBill creditActiveBill = creditBillDao.read(creditBillActiveId);
                    CreditBill creditPassiveBill = creditBillDao.read(creditBillPassiveId);

                    creditPassiveBill.setCredit(creditDao.read(creditPassiveBill.getCredit().getId()));

                    double moneyToBank = 0;
                    switch (creditPassiveBill.getCredit().getType()) {
                        case MONTHLY: {
                            moneyToBank = creditActiveBill.getMoneySum() + creditPassiveBill.getMoneySum();
                            break;
                        }
                        case ANNUITY: {
                            moneyToBank = creditActiveBill.getMoneySum();
                        }
                    }

                    switch (creditPassiveBill.getCredit().getCurrency()) {
                        case USD: {
                            BankBillsCreator.usdBankBill.setMoneyAmount(BankBillsCreator.usdBankBill.getMoneyAmount() + moneyToBank);
                            break;
                        }
                        case BYN: {
                            BankBillsCreator.bynBankBill.setMoneyAmount(BankBillsCreator.bynBankBill.getMoneyAmount() + moneyToBank);
                        }
                    }

                    creditBillDao.delete(creditBillActiveId);
                    creditBillDao.delete(creditBillPassiveId);

                    page = "/controller?command=allClients";
                    break;
                }
                case "atmMainPage" : {
                    Card card = cardDao.readByNumber(req.getParameter("number"));
                    CreditBill creditBill = creditBillDao.read(card.getCreditBill().getId());
                    creditBill.setCredit(creditDao.read(creditBill.getCredit().getId()));
                    card.setCreditBill(creditBill);
                    card.setClient(clientDao.read(card.getClient().getIdClient()));
                    HttpSession session = req.getSession(true);
                    if (session != null) {
                        session.setAttribute("card", card);
                    }
                    page = "/atm_main_page.jsp";
                    break;
                }

                case "billStatus" : {
                    Card card = (Card) req.getSession().getAttribute("card");
                    page = "/controller?command=atmMainPage&number="+card.getNumber()+"&subCommand=billStatus";
                    break;
                }

                case "openWithdrawCash" : {
                    Card card = (Card) req.getSession().getAttribute("card");
                    page = "/controller?command=atmMainPage&number="+card.getNumber()+"&subCommand=openWithdrawCash";
                    break;
                }

                case "openMobilePayment" : {
                    Card card = (Card) req.getSession().getAttribute("card");
                    page = "/controller?command=atmMainPage&number="+card.getNumber()+"&subCommand=openMobilePayment";
                    break;
                }

            }
        }

        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(page);
        requestDispatcher.forward(req, resp);

        //resp.sendRedirect(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = null;
        String command = req.getParameter("command");
        boolean isErrorExist = false;

        if (command != null) {
            switch (command) {

                case "mobilePayment" : {
                    int sum = Integer.parseInt(req.getParameter("sum"));
                    Card card = (Card) req.getSession().getAttribute("card");

                    if (card.getCreditBill().getMoneySum() - sum < 0) {
                        page = "/controller?command=atmMainPage&number=" + card.getNumber()
                                + "&subCommand=openMobilePayment&subCommandError=insufficientlyMoney";
                    }
                    else {
                        CreditBill creditBill = card.getCreditBill();
                        creditBill.setMoneySum(creditBill.getMoneySum() - sum);
                        creditBillDao.update(creditBill);
                        page = "/controller?command=atmMainPage&number=" + card.getNumber();
                    }
                    break;
                }

                case "withDrawCash" : {
                    int sum = Integer.parseInt(req.getParameter("sum"));
                    Card card = (Card) req.getSession().getAttribute("card");

                    if (card.getCreditBill().getMoneySum() - sum < 0) {
                        page = "/controller?command=atmMainPage&number=" + card.getNumber()
                                + "&subCommand=openWithdrawCash&subCommandError=insufficientlyMoney";
                    }
                    else {
                        CreditBill creditBill = card.getCreditBill();
                        creditBill.setMoneySum(creditBill.getMoneySum() - sum);
                        creditBillDao.update(creditBill);
                        page = "/controller?command=atmMainPage&number=" + card.getNumber();
                    }
                    break;
                }

                case "login" : {
                    String number = req.getParameter("number");
                    int pin = Integer.parseInt(req.getParameter("pin"));

                    Card card = cardDao.readByNumberAndPin(number, pin);
                    if (card == null) {
                        page = "bankomat_login.jsp?message=error";
                    }
                    else {
                        page = "/controller?command=atmMainPage&number=" + number;
                    }

                    break;
                }

                case "createDepositForClient" : {
                    Client client = new Client();
                    client.setIdClient(Integer.parseInt(req.getParameter("clientId")));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date startDate = new Date(dateFormat.parse(req.getParameter("startDate")).getTime());
                        Date endDate = new Date(dateFormat.parse(req.getParameter("endDate")).getTime());
                        int depositId = Integer.parseInt(req.getParameter("depositId"));
                        double moneySum = Double.parseDouble(req.getParameter("sum"));

                        Bill bill = new Bill();
                        bill.setEndDate(endDate);
                        bill.setStartDate(startDate);
                        bill.setMoneySum(moneySum);
                        bill.setDeposit(new Deposit(){{setId(depositId);}});
                        bill.setCreator(client);
                        Deposit tempDep = depositDao.read(depositId);

                        String billNumber;
                        if (tempDep.getType().equals(DepositType.REVOCABLE)) {
                            billNumber = BillsUtil.REVOCABLE_BILL_NUMBER + BillsUtil.generateBillNumber() + "0";
                            bill.setNumber(billNumber);
                        }
                        else {
                            billNumber = BillsUtil.IRREVOCABLE_BILL_NUMBER + BillsUtil.generateBillNumber() + "0";
                            bill.setNumber(billNumber);
                        }

                        Bill activeBill = new Bill() {{
                            setEndDate(endDate);
                            setStartDate(startDate);
                            setMoneySum(0);
                            setDeposit(new Deposit(){{setId(depositId);}});
                            setCreator(client);
                            setNumber(billNumber.substring(0, billNumber.length()-1) + "1");
                        }};

                        billDao.create(bill);
                        billDao.create(activeBill);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    page = "/controller?command=allClients";
                    break;
                }

                case "createCreditForClient" : {
                    Client client = new Client();
                    client.setIdClient(Integer.parseInt(req.getParameter("clientId")));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date startDate = new Date(dateFormat.parse(req.getParameter("startDate")).getTime());
                        Date endDate = new Date(dateFormat.parse(req.getParameter("endDate")).getTime());
                        int creditId = Integer.parseInt(req.getParameter("creditId"));
                        double moneySum = Double.parseDouble(req.getParameter("sum"));

                        CreditBill bill = new CreditBill();
                        bill.setEndDate(endDate);
                        bill.setStartDate(startDate);
                        bill.setMoneySum(moneySum);
                        bill.setCredit(new Credit(){{setId(creditId);}});
                        bill.setCreator(client);
                        Credit choosenCredit = creditDao.read(creditId);

                        String billNumber;
                        if (choosenCredit.getType().equals(CreditType.ANNUITY)) {
                            billNumber = BillsUtil.ANNUITY_BILL_NUMBER + BillsUtil.generateBillNumber() + "0";
                            bill.setNumber(billNumber);
                        }
                        else {
                            billNumber = BillsUtil.MONTHLY_BILL_NUMBER + BillsUtil.generateBillNumber() + "0";
                            bill.setNumber(billNumber);
                        }

                        CreditBill activeBill = new CreditBill() {{
                            setEndDate(endDate);
                            setStartDate(startDate);
                            setMoneySum(0);
                            setCredit(new Credit(){{setId(creditId);}});
                            setCreator(client);
                            setNumber(billNumber.substring(0, billNumber.length()-1) + "1");
                        }};

                        creditBillDao.create(bill);
                        creditBillDao.create(activeBill);

                        switch (choosenCredit.getCurrency()) {
                            case USD: {
                                BankBillsCreator.usdBankBill.setMoneyAmount(BankBillsCreator.usdBankBill.getMoneyAmount() - moneySum);
                                break;
                            }
                            case BYN: {
                                BankBillsCreator.bynBankBill.setMoneyAmount(BankBillsCreator.bynBankBill.getMoneyAmount() - moneySum);
                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    page = "/controller?command=allClients";
                    break;
                }

                case "createClient": {
                    if (validateClient(req)) {
                        Client client = new Client();
                        client.setSecName(req.getParameter("firstName"));
                        client.setName(req.getParameter("name"));
                        client.setSurName(req.getParameter("surname"));

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                        try {
                            client.setBirthday(new java.sql.Date(dateFormat.parse(req.getParameter("birthDay")).getTime()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (req.getParameter("sexRadio").equals("m")) {
                            client.setSex(true);
                        } else {
                            client.setSex(false);
                        }

                        client.setPassportSeries(req.getParameter("passportSeries"));
                        client.setPassportNumber(req.getParameter("passportNo"));
                        client.setPassportPlace(req.getParameter("passportPlace"));
                        try {
                            client.setPassportDate(new Date(dateFormat.parse(req.getParameter("passportDate")).getTime()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        client.setPassportId(req.getParameter("passportIdNo"));
                        client.setBirthPlace(req.getParameter("birthPlace"));

                        String[] citiesArr = req.getParameter("livingCity").replaceAll(" ", "").split(",");
                        List<String> cities = new ArrayList<>();
                        Collections.addAll(cities, citiesArr);
                        client.setLivingCities(cities);
                        client.setAddress(req.getParameter("address"));
                        client.setHomePhone(req.getParameter("homePhone"));
                        client.setPhone(req.getParameter("phone"));
                        client.setEmail(req.getParameter("email"));

                        List<String> sp = new ArrayList<>();
                        Collections.addAll(sp, req.getParameter("sp").split(","));
                        client.setFamilyMembers(sp);

                        List<String> nationalities = new ArrayList<>();
                        Collections.addAll(nationalities, req.getParameter("nationality").replaceAll(" ", "").split(","));
                        client.setNationalities(nationalities);

                        List<String> ills = new ArrayList<>();
                        Collections.addAll(ills, req.getParameter("ills").split(","));
                        client.setIlls(ills);

                        if (req.getParameterValues("pensioner") == null) {
                            client.setPensioner(false);
                        } else {
                            client.setPensioner(true);
                        }

                        if (req.getParameterValues("military") == null) {
                            client.setMilitary(false);
                        } else {
                            client.setMilitary(true);
                        }

                        client.setIncome(Integer.parseInt(req.getParameter("income")));

                        if (validateClient(req)) {

                            if (checkByFullName(client.getSecName(), client.getName(), client.getSurName())) {
                                req.getSession().setAttribute("client", client);
                                page = "/controller?command=allClients&message=fullNameMatch";
                            } else if (checkPassportNumber(client.getPassportNumber())) {
                                req.getSession().setAttribute("client", client);
                                page = "/controller?command=allClients&message=passportNoMatch";
                            } else if (checkByPassportId(client.getPassportId())) {
                                req.getSession().setAttribute("client", client);
                                page = "/controller?command=allClients&message=passpordIdMatch";
                            } else {
                                req.getSession().removeAttribute("client");
                                clientDao.create(client);
                                page = "/controller?command=allClients";
                            }
                        } else {
                            req.getSession().setAttribute("client", client);
                            page = "/controller?command=allClients&message=invalidData";
                        }
                    }
                    break;
                }
                case "updateClient": {
                    Client client = new Client();
                    client.setIdClient(Integer.parseInt(req.getParameter("clientId")));
                    client.setSecName(req.getParameter("firstName"));
                    client.setName(req.getParameter("name"));
                    client.setSurName(req.getParameter("surname"));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                    try {
                        client.setBirthday(new java.sql.Date(dateFormat.parse(req.getParameter("birthDay")).getTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (req.getParameter("sexRadio").equals("m")) {
                        client.setSex(true);
                    } else {
                        client.setSex(false);
                    }

                    client.setPassportSeries(req.getParameter("passportSeries"));
                    client.setPassportNumber(req.getParameter("passportNo"));
                    client.setPassportPlace(req.getParameter("passportPlace"));
                    try {
                        client.setPassportDate(new Date(dateFormat.parse(req.getParameter("passportDate")).getTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    client.setPassportId(req.getParameter("passportIdNo"));
                    client.setBirthPlace(req.getParameter("birthPlace"));

                    String[] citiesArr = req.getParameter("livingCity").replaceAll(" ", "").split(",");
                    List<String> cities = new ArrayList<>();
                    Collections.addAll(cities, citiesArr);
                    client.setLivingCities(cities);
                    client.setAddress(req.getParameter("address"));
                    client.setHomePhone(req.getParameter("homePhone"));
                    client.setPhone(req.getParameter("phone"));
                    client.setEmail(req.getParameter("email"));

                    List<String> sp = new ArrayList<>();
                    Collections.addAll(sp, req.getParameter("sp").split(","));
                    client.setFamilyMembers(sp);

                    List<String> nationalities = new ArrayList<>();
                    Collections.addAll(nationalities, req.getParameter("nationality").replaceAll(" ", "").split(","));
                    client.setNationalities(nationalities);

                    List<String> ills = new ArrayList<>();
                    Collections.addAll(ills, req.getParameter("ills").split(","));
                    client.setIlls(ills);

                    if (req.getParameterValues("pensioner") == null) {
                        client.setPensioner(false);
                    } else {
                        client.setPensioner(true);
                    }

                    if (req.getParameterValues("military") == null) {
                        client.setMilitary(false);
                    } else {
                        client.setMilitary(true);
                    }

                    client.setIncome(Integer.parseInt(req.getParameter("income")));

                    Client existingClient;
                    if ((existingClient = clientDao.readByPassportId(client.getPassportId())) != null) {
                        if (!existingClient.getSecName().equals(client.getSecName())) {
                            req.getSession().setAttribute("client", client);
                            page = "/controller?command=openForEdit&message=passpordIdMatch";
                        }
                    }

                    if ((existingClient = clientDao.readByPassportNumber(client.getPassportNumber())) != null) {
                        if (!existingClient.getSecName().equals(client.getSecName())) {
                            req.getSession().setAttribute("client", client);
                            page = "/controller?command=openForEdit&message=passportNoMatch";
                        }
                    }

                    if (page == null) {

                        if (validateClient(req)) {
                            req.getSession().removeAttribute("client");
                            clientDao.update(client);
                            page = "/controller?command=allClients";
                        } else {
                            req.getSession().setAttribute("client", client);
                            page = "/controller?command=openForEdit&message=invalidData";
                        }
                    }
                    break;
                }
            }
        }
        resp.sendRedirect(page);
    }

    private boolean checkPassportNumber(String passportNumber) {
        return clientDao.readByPassportNumber(passportNumber) != null;
    }

    private boolean checkByFullName(String secName, String name, String surName) {
        return clientDao.readByFullName(secName, name, surName) != null;
    }

    private boolean checkByPassportId(String passportId) {
        return clientDao.readByPassportId(passportId) != null;
    }

    private boolean validateClient(HttpServletRequest req) {
        if (req.getParameter("firstName").isEmpty() || req.getParameter("firstName").length() > 45) return false;
        if (req.getParameter("name").isEmpty() || req.getParameter("name").length() > 45) return false;
        if (req.getParameter("surname").isEmpty() || req.getParameter("surname").length() > 45) return false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        try {
            new java.sql.Date(dateFormat.parse(req.getParameter("birthDay")).getTime());
            new Date(dateFormat.parse(req.getParameter("passportDate")).getTime());
        } catch (ParseException e) {
            return false;
        }
        if (req.getParameter("sexRadio").isEmpty()) return false;
        if (req.getParameter("passportSeries").isEmpty() || req.getParameter("passportSeries").length() > 2)
            return false;
        if (req.getParameter("passportNo").isEmpty() || req.getParameter("passportNo").length() > 45) return false;
        if (req.getParameter("passportPlace").isEmpty() || req.getParameter("passportPlace").length() > 150)
            return false;
        if (req.getParameter("passportIdNo").isEmpty() || req.getParameter("passportIdNo").length() > 45) return false;
        if (req.getParameter("birthPlace").isEmpty() || req.getParameter("birthPlace").length() > 150) return false;
        if (req.getParameter("address").isEmpty() || req.getParameter("address").length() > 150) return false;
        if (req.getParameter("homePhone").length() > 45) return false;
        if (req.getParameter("phone").length() > 45) return false;
        if (req.getParameter("email").length() > 45) return false;
        if (!req.getParameter("income").isEmpty()) {
            try {
                Integer.parseInt(req.getParameter("income"));
            } catch (NumberFormatException e) {
                return false;
            }
        }
        String[] cities = req.getParameter("livingCity").replaceAll(" ", "").split(",");
        if (cities.length < 1) return false;

        if (req.getParameter("sp").split(",").length < 1) return false;

        if (req.getParameter("nationality").replaceAll(" ", "").split(",").length < 1) return false;

        if (req.getParameter("ills").split(",").length < 1) return false;

        return true;
    }
}
