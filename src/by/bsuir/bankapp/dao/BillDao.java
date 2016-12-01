package by.bsuir.bankapp.dao;

import by.bsuir.bankapp.bean.Bill;
import by.bsuir.bankapp.bean.Client;
import by.bsuir.bankapp.bean.Deposit;
import by.bsuir.bankapp.dao.connectionpool.ConnectionPoolImpl;
import by.bsuir.bankapp.dao.connectionpool.IConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDao implements GenericDao<Integer, Bill> {

    private static final String SQL_READ_BILL_BY_ID = "select * from bank_app.bill where id = ?";

    private static final BillDao INSTACE = new BillDao();

    private BillDao(){}

    public static BillDao getInstace() {
        return INSTACE;
    }

    @Override
    public Bill read(Integer key) {
        Bill bill = null;
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_BILL_BY_ID)) {
            statement.setInt(1, key);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    bill = new Bill();
                    bill.setId(set.getInt(1));
                    bill.setDeposit(new Deposit(){{setId(set.getInt(4));}});
                    bill.setCreator(new Client(){{setIdClient(set.getInt(5));}});
                    bill.setNumber(set.getString(2));
                    bill.setMoneySum(set.getDouble(3));
                    bill.setStartDate(set.getDate(6));
                    bill.setEndDate(set.getDate(7));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bill;
    }

    private static final String SQL_UPDATE_BILL = "UPDATE bank_app.bill set money_sum = ? where id = ?";
    @Override
    public void update(Bill obj) {
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

    private static final String SQL_REMOVE_BILL = "DELETE FROM bank_app.bill WHERE id = ?";
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

    private static final java.lang.String SQL_CREATE_BILL = "INSERT INTO bank_app.bill (number, money_sum, deposit_id, client_id, start_date, end_date) VALUES (?,?,?,?,?,?)";

    @Override
    public void create(Bill obj) {
        IConnectionPool pool = ConnectionPoolImpl.getInstance();
        try (Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_CREATE_BILL)) {
            statement.setString(1, obj.getNumber());
            statement.setDouble(2, obj.getMoneySum());
            statement.setInt(3, obj.getDeposit().getId());
            statement.setInt(4, obj.getCreator().getIdClient());
            statement.setDate(5, new Date(obj.getStartDate().getTime()));
            statement.setDate(6, new Date(obj.getEndDate().getTime()));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final String SQL_READ_BILL_ALL = "select * from bank_app.bill";
    @Override
    public List<Bill> readAll() {
        List<Bill> bills = new ArrayList<>();
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_BILL_ALL)) {
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    Bill bill = new Bill();
                    bill.setId(set.getInt(1));
                    bill.setDeposit(new Deposit(){{setId(set.getInt(4));}});
                    bill.setCreator(new Client(){{setIdClient(set.getInt(5));}});
                    bill.setNumber(set.getString(2));
                    bill.setMoneySum(set.getDouble(3));
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
