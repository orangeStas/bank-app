package by.bsuir.bankapp.dao;

import by.bsuir.bankapp.bean.Card;
import by.bsuir.bankapp.bean.Client;
import by.bsuir.bankapp.bean.CreditBill;
import by.bsuir.bankapp.dao.connectionpool.ConnectionPoolImpl;
import by.bsuir.bankapp.dao.connectionpool.IConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CardDao implements GenericDao<Integer, Card> {

    private static final CardDao INSTANCE = new CardDao();

    private CardDao(){}

    public static CardDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Card read(Integer key) {
        return null;
    }

    public Card readByNumberAndPin(String number, int pin) {
        Card card = null;
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

        try (Connection connection= connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM bank_app.card WHERE number = ? AND pin = ?")) {
            statement.setString(1, number);
            statement.setInt(2, pin);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    card = new Card(){{
                        setNumber(number);
                        setPin(pin);
                        setCreditBill(new CreditBill() {{
                            setId(resultSet.getInt(3));
                        }});
                        setClient(new Client() {{
                            setIdClient(resultSet.getInt(4));
                        }});
                    }};

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return card;
    }

    public Card readByNumber(String number) {
        Card card = null;
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

        try (Connection connection= connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM bank_app.card WHERE number = ?")) {
            statement.setString(1, number);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    card = new Card(){{
                        setNumber(number);
                        setPin(resultSet.getInt(2));
                        setCreditBill(new CreditBill() {{
                            setId(resultSet.getInt(3));
                        }});
                        setClient(new Client() {{
                            setIdClient(resultSet.getInt(4));
                        }});
                    }};

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return card;
    }

    @Override
    public void update(Card obj) {

    }

    @Override
    public void delete(Integer key) {

    }

    @Override
    public void create(Card obj) {

    }

    @Override
    public List<Card> readAll() {
        return null;
    }
}
