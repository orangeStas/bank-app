package by.bsuir.bankapp.dao;

import by.bsuir.bankapp.bean.*;
import by.bsuir.bankapp.dao.connectionpool.ConnectionPoolImpl;
import by.bsuir.bankapp.dao.connectionpool.IConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditDao implements GenericDao<Integer, Credit> {

    private static final CreditDao INSTANCE = new CreditDao();
    public static CreditDao getInstance() {
        return INSTANCE;
    }

    private CreditDao(){}

    private static final String SQL_READ_BY_ID = "SELECT * FROM bank_app.credit WHERE id = ?";


    @Override
    public Credit read(Integer key) {
        Credit credit = null;
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_BY_ID)) {
            statement.setInt(1, key);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    credit = new Credit();
                    credit.setId(resultSet.getInt(1));
                    credit.setType(CreditType.valueOf(resultSet.getString(2)));
                    credit.setPercent(resultSet.getDouble(3));
                    credit.setName(resultSet.getString(4));
                    credit.setCurrency(Currency.valueOf(resultSet.getString(5)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return credit;
    }

    @Override
    public void update(Credit obj) {

    }

    @Override
    public void delete(Integer key) {

    }

    @Override
    public void create(Credit obj) {

    }

    private static final String SQL_READ_ALL_CREDITS = "select * from bank_app.credit";


    @Override
    public List<Credit> readAll() {
        List<Credit> credits = new ArrayList<>();
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_ALL_CREDITS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Credit credit = new Credit();
                    credit.setId(resultSet.getInt(1));
                    credit.setType(CreditType.valueOf(resultSet.getString(2)));
                    credit.setPercent(resultSet.getDouble(3));
                    credit.setName(resultSet.getString(4));
                    credit.setCurrency(Currency.valueOf(resultSet.getString(5)));
                    credits.add(credit);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return credits;
    }
}
