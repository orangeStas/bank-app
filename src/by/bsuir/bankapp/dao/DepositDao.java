package by.bsuir.bankapp.dao;

import by.bsuir.bankapp.bean.Currency;
import by.bsuir.bankapp.bean.Deposit;
import by.bsuir.bankapp.bean.DepositType;
import by.bsuir.bankapp.dao.connectionpool.ConnectionPoolImpl;
import by.bsuir.bankapp.dao.connectionpool.IConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepositDao implements GenericDao<Integer, Deposit> {

    private static final DepositDao INSTANCE = new DepositDao();

    public static DepositDao getInstance() {
        return INSTANCE;
    }

    private DepositDao() {
    }


    private static final String SQL_READ_BY_ID = "SELECT * FROM bank_app.deposit WHERE id = ?";

    @Override
    public Deposit read(Integer key) {
        Deposit deposit = null;
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_BY_ID)) {
            statement.setInt(1, key);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    deposit = new Deposit();
                    deposit.setId(resultSet.getInt(1));
                    deposit.setType(DepositType.valueOf(resultSet.getString(2)));
                    deposit.setPercent(resultSet.getDouble(3));
                    deposit.setName(resultSet.getString(4));
                    deposit.setCurrency(Currency.valueOf(resultSet.getString(5)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deposit;
    }

    @Override
    public void update(Deposit obj) {

    }

    @Override
    public void delete(Integer key) {

    }

    @Override
    public void create(Deposit obj) {

    }


    private static final String SQL_READ_ALL_DEPOSITS = "select * from bank_app.deposit";

    @Override
    public List<Deposit> readAll() {
        List<Deposit> deposits = new ArrayList<>();
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_ALL_DEPOSITS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Deposit deposit = new Deposit();
                    deposit.setId(resultSet.getInt(1));
                    deposit.setType(DepositType.valueOf(resultSet.getString(2)));
                    deposit.setPercent(resultSet.getDouble(3));
                    deposit.setName(resultSet.getString(4));
                    deposit.setCurrency(Currency.valueOf(resultSet.getString(5)));
                    deposits.add(deposit);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deposits;
    }
}
