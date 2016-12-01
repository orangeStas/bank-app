package by.bsuir.bankapp.dao;

import by.bsuir.bankapp.bean.Client;
import by.bsuir.bankapp.bean.Credit;
import by.bsuir.bankapp.bean.CreditBill;
import by.bsuir.bankapp.dao.connectionpool.ConnectionPoolImpl;
import by.bsuir.bankapp.dao.connectionpool.IConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreditBillDao implements GenericDao<Integer, CreditBill> {

    private static final CreditBillDao INSTANCE = new CreditBillDao();

    private CreditBillDao(){}

    public static CreditBillDao getInstance() {
        return INSTANCE;
    }

    private static final String SQL_READ_BILL_BY_ID = "select * from bank_app.bill_credit where id = ?";


    @Override
    public CreditBill read(Integer key) {
        CreditBill bill = null;
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_BILL_BY_ID)) {
            statement.setInt(1, key);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    bill = new CreditBill();
                    bill.setId(set.getInt(1));
                    bill.setCredit(new Credit(){{setId(set.getInt(2));}});
                    bill.setCreator(new Client(){{setIdClient(set.getInt(3));}});
                    bill.setNumber(set.getString(4));
                    bill.setMoneySum(set.getDouble(5));
                    bill.setStartDate(set.getDate(6));
                    bill.setEndDate(set.getDate(7));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bill;
    }


    private static final String SQL_UPDATE_BILL = "UPDATE bank_app.bill_credit set money_sum = ? where id = ?";

    @Override
    public void update(CreditBill obj) {
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BILL)) {
            statement.setDouble(1, obj.getMoneySum());
            statement.setInt(2, obj.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final String SQL_REMOVE_BILL = "DELETE FROM bank_app.bill_credit WHERE id = ?";
    @Override
    public void delete(Integer key) {
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_BILL)) {
            statement.setInt(1, key);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final java.lang.String SQL_CREATE_BILL = "INSERT INTO bank_app.bill_credit (number, money_sum, credit_id, client_id, start_date, end_date) VALUES (?,?,?,?,?,?)";
    @Override
    public void create(CreditBill obj) {
        IConnectionPool pool = ConnectionPoolImpl.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE_BILL)) {
            statement.setString(1, obj.getNumber());
            statement.setDouble(2, obj.getMoneySum());
            statement.setInt(3, obj.getCredit().getId());
            statement.setInt(4, obj.getCreator().getIdClient());
            statement.setDate(5, new Date(obj.getStartDate().getTime()));
            statement.setDate(6, new Date(obj.getEndDate().getTime()));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static final String SQL_READ_BILL_ALL = "select * from bank_app.bill_credit";
    @Override
    public List<CreditBill> readAll() {
        List<CreditBill> bills = new ArrayList<>();
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_BILL_ALL)) {
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    CreditBill bill = new CreditBill();
                    bill.setId(set.getInt(1));
                    bill.setCredit(new Credit(){{setId(set.getInt(2));}});
                    bill.setCreator(new Client(){{setIdClient(set.getInt(3));}});
                    bill.setNumber(set.getString(4));
                    bill.setMoneySum(set.getDouble(5));
                    bill.setStartDate(set.getDate(6));
                    bill.setEndDate(set.getDate(7));
                    bills.add(bill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bills;
    }
}
