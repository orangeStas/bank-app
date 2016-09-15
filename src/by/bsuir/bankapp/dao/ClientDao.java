package by.bsuir.bankapp.dao;

import by.bsuir.bankapp.bean.Client;
import by.bsuir.bankapp.dao.connectionpool.ConnectionPoolImpl;
import by.bsuir.bankapp.dao.connectionpool.IConnectionPool;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDao implements GenericDao<Integer, Client> {

    private static final ClientDao instance = new ClientDao();

    private ClientDao() {}

    public static ClientDao getInstance() {
        return instance;
    }

    @Override
    public Client read(Integer key) {
        Client client = null;
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from bank_app.client where idclient = ?")) {
            statement.setInt(1, key);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    client = new Client();
                    client.setIdClient(resultSet.getInt(1));
                    client.setSecName(resultSet.getString(2));
                    client.setName(resultSet.getString(3));
                    client.setSurName(resultSet.getString(4));
                    client.setBirthday(resultSet.getDate(5));
                    client.setSex(resultSet.getBoolean(6));
                    client.setPassportSeries(resultSet.getString(7));
                    client.setPassportNumber(resultSet.getString(8));
                    client.setPassportPlace(resultSet.getString(9));
                    client.setPassportDate(resultSet.getDate(10));
                    client.setPassportId(resultSet.getString(11));
                    client.setBirthPlace(resultSet.getString(12));
                    client.setAddress(resultSet.getString(13));
                    client.setHomePhone(resultSet.getString(14));
                    client.setPhone(resultSet.getString(15));
                    client.setEmail(resultSet.getString(16));
                    client.setPensioner(resultSet.getBoolean(17));
                    client.setIncome(resultSet.getInt(18));
                    client.setMilitary(resultSet.getBoolean(19));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        client.setIlls(readIllsOfClient(client.getIdClient()));
        client.setNationalities(readNationalitiesOfClient(client.getIdClient()));
        client.setFamilyMembers(readFamilyMembersOfClient(client.getIdClient()));
        client.setLivingCities(readLivingCitiesOfClient(client.getIdClient()));

        return client;
    }


    public Client readByPassportNumber(String passport) {
        Client client = null;
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from bank_app.client where passportNumber = ?")) {
            statement.setString(1, passport);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    client = new Client();
                    client.setIdClient(resultSet.getInt(1));
                    client.setSecName(resultSet.getString(2));
                    client.setName(resultSet.getString(3));
                    client.setSurName(resultSet.getString(4));
                    client.setBirthday(resultSet.getDate(5));
                    client.setSex(resultSet.getBoolean(6));
                    client.setPassportSeries(resultSet.getString(7));
                    client.setPassportNumber(resultSet.getString(8));
                    client.setPassportPlace(resultSet.getString(9));
                    client.setPassportDate(resultSet.getDate(10));
                    client.setPassportId(resultSet.getString(11));
                    client.setBirthPlace(resultSet.getString(12));
                    client.setAddress(resultSet.getString(13));
                    client.setHomePhone(resultSet.getString(14));
                    client.setPhone(resultSet.getString(15));
                    client.setEmail(resultSet.getString(16));
                    client.setPensioner(resultSet.getBoolean(17));
                    client.setIncome(resultSet.getInt(18));
                    client.setMilitary(resultSet.getBoolean(19));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        client.setIlls(readIllsOfClient(client.getIdClient()));
        client.setNationalities(readNationalitiesOfClient(client.getIdClient()));
        client.setFamilyMembers(readFamilyMembersOfClient(client.getIdClient()));
        client.setLivingCities(readLivingCitiesOfClient(client.getIdClient()));

        return client;
    }

    public Client readByFullName(String secName, String name, String surname) {
        Client client = null;
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from bank_app.client where secname = ? AND  name=? AND client.surname=?")) {
            statement.setString(1, secName);
            statement.setString(2, name);
            statement.setString(3, surname);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    client = new Client();
                    client.setIdClient(resultSet.getInt(1));
                    client.setSecName(resultSet.getString(2));
                    client.setName(resultSet.getString(3));
                    client.setSurName(resultSet.getString(4));
                    client.setBirthday(resultSet.getDate(5));
                    client.setSex(resultSet.getBoolean(6));
                    client.setPassportSeries(resultSet.getString(7));
                    client.setPassportNumber(resultSet.getString(8));
                    client.setPassportPlace(resultSet.getString(9));
                    client.setPassportDate(resultSet.getDate(10));
                    client.setPassportId(resultSet.getString(11));
                    client.setBirthPlace(resultSet.getString(12));
                    client.setAddress(resultSet.getString(13));
                    client.setHomePhone(resultSet.getString(14));
                    client.setPhone(resultSet.getString(15));
                    client.setEmail(resultSet.getString(16));
                    client.setPensioner(resultSet.getBoolean(17));
                    client.setIncome(resultSet.getInt(18));
                    client.setMilitary(resultSet.getBoolean(19));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        client.setIlls(readIllsOfClient(client.getIdClient()));
        client.setNationalities(readNationalitiesOfClient(client.getIdClient()));
        client.setFamilyMembers(readFamilyMembersOfClient(client.getIdClient()));
        client.setLivingCities(readLivingCitiesOfClient(client.getIdClient()));

        return client;
    }

    public Client readByPassportId(String passportId) {
        Client client = null;
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from bank_app.client where passportId = ?")) {
            statement.setString(1, passportId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    client = new Client();
                    client.setIdClient(resultSet.getInt(1));
                    client.setSecName(resultSet.getString(2));
                    client.setName(resultSet.getString(3));
                    client.setSurName(resultSet.getString(4));
                    client.setBirthday(resultSet.getDate(5));
                    client.setSex(resultSet.getBoolean(6));
                    client.setPassportSeries(resultSet.getString(7));
                    client.setPassportNumber(resultSet.getString(8));
                    client.setPassportPlace(resultSet.getString(9));
                    client.setPassportDate(resultSet.getDate(10));
                    client.setPassportId(resultSet.getString(11));
                    client.setBirthPlace(resultSet.getString(12));
                    client.setAddress(resultSet.getString(13));
                    client.setHomePhone(resultSet.getString(14));
                    client.setPhone(resultSet.getString(15));
                    client.setEmail(resultSet.getString(16));
                    client.setPensioner(resultSet.getBoolean(17));
                    client.setIncome(resultSet.getInt(18));
                    client.setMilitary(resultSet.getBoolean(19));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        client.setIlls(readIllsOfClient(client.getIdClient()));
        client.setNationalities(readNationalitiesOfClient(client.getIdClient()));
        client.setFamilyMembers(readFamilyMembersOfClient(client.getIdClient()));
        client.setLivingCities(readLivingCitiesOfClient(client.getIdClient()));

        return client;
    }

    public List<String> readFamilyMembersOfClient(int clientId) {
        List<String> familyMembers = new ArrayList<>();
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT bank_app.family_stat.family_member FROM bank_app.family_stat WHERE clientId=?")) {
            statement.setInt(1, clientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    familyMembers.add(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return familyMembers;
    }

    public List<String> readLivingCitiesOfClient(int clientId) {
        List<String> livingCities = new ArrayList<>();
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT bank_app.city_living.city_name FROM bank_app.city_living WHERE clientId=?")) {
            statement.setInt(1, clientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    livingCities.add(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livingCities;
    }

    public List<String> readNationalitiesOfClient(int clientId) {
        List<String> nationalities = new ArrayList<>();
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT bank_app.nationality.nationality FROM bank_app.nationality WHERE clientId=?")) {
            statement.setInt(1, clientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    nationalities.add(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nationalities;
    }

    public List<String> readIllsOfClient(int clientId) {
        List<String> ills = new ArrayList<>();
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT bank_app.ills.illName FROM bank_app.ills WHERE clientId=?")) {
            statement.setInt(1, clientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ills.add(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ills;
    }

    @Override
    public void update(Client obj) {

    }

    @Override
    public void delete(Integer key) {
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try (Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM bank_app.client WHERE bank_app.client.idclient = ?")) {
            statement.setInt(1, key);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByPassportNumber(String passportNumber) {
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM bank_app.client WHERE bank_app.client.passportNumber = ?")) {
            statement.setString(1, passportNumber);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Client obj) {
        int clientId = -1;
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO bank_app.client " +
                "(secname, name, surname, birthday, sex, passportSeries, passportNumber, passportPlace, passportDate, passportId, birthPlace, address, homePhone, phone, `e-mail`, pensioner, income, military) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, obj.getSecName());
            statement.setString(2, obj.getName());
            statement.setString(3, obj.getSurName());
            statement.setDate(4, obj.getBirthday());
            statement.setBoolean(5, obj.isSex());
            statement.setString(6, obj.getPassportSeries());
            statement.setString(7, obj.getPassportNumber());
            statement.setString(8, obj.getPassportPlace());
            statement.setDate(9, obj.getPassportDate());
            statement.setString(10, obj.getPassportId());
            statement.setString(11, obj.getBirthPlace());
            statement.setString(12, obj.getAddress());
            statement.setString(13, obj.getHomePhone());
            statement.setString(14, obj.getPhone());
            statement.setString(15, obj.getEmail());
            statement.setBoolean(16, obj.isPensioner());
            statement.setInt(17, obj.getIncome());
            statement.setBoolean(18, obj.isMilitary());
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    clientId = generatedKeys.getInt(1);
                }
            }

            for (String familyMember : obj.getFamilyMembers()) {
                try (Connection connection1 = connectionPool.getConnection();
                    PreparedStatement statement1 = connection1.prepareStatement("INSERT INTO bank_app.family_stat (clientId, family_member) VALUES (?, ?)")) {
                    statement1.setInt(1, clientId);
                    statement1.setString(2, familyMember);
                    statement1.execute();
                }
            }

            for (String ill : obj.getIlls()) {
                try (Connection connection1 = connectionPool.getConnection();
                     PreparedStatement statement1 = connection1.prepareStatement("INSERT INTO bank_app.ills (clientId, illName) VALUES (?, ?)")) {
                    statement1.setInt(1, clientId);
                    statement1.setString(2, ill);
                    statement1.execute();
                }
            }

            for (String nationality : obj.getNationalities()) {
                try (Connection connection1 = connectionPool.getConnection();
                     PreparedStatement statement1 = connection1.prepareStatement("INSERT INTO bank_app.nationality (clientId, nationality) VALUES (?, ?)")) {
                    statement1.setInt(1, clientId);
                    statement1.setString(2, nationality);
                    statement1.execute();
                }
            }

            for (String city : obj.getLivingCities()) {
                try (Connection connection1 = connectionPool.getConnection();
                     PreparedStatement statement1 = connection1.prepareStatement("INSERT INTO bank_app.city_living (clientId, city_name) VALUES (?, ?)")) {
                    statement1.setInt(1, clientId);
                    statement1.setString(2, city);
                    statement1.execute();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Client> readAll() {
        List<Client> clients = new ArrayList<>();
        IConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from bank_app.client")) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Client client = new Client();
                    client.setIdClient(resultSet.getInt(1));
                    client.setSecName(resultSet.getString(2));
                    client.setName(resultSet.getString(3));
                    client.setSurName(resultSet.getString(4));
                    client.setBirthday(resultSet.getDate(5));
                    client.setSex(resultSet.getBoolean(6));
                    client.setPassportSeries(resultSet.getString(7));
                    client.setPassportNumber(resultSet.getString(8));
                    client.setPassportPlace(resultSet.getString(9));
                    client.setPassportDate(resultSet.getDate(10));
                    client.setPassportId(resultSet.getString(11));
                    client.setBirthPlace(resultSet.getString(12));
                    client.setAddress(resultSet.getString(13));
                    client.setHomePhone(resultSet.getString(14));
                    client.setPhone(resultSet.getString(15));
                    client.setEmail(resultSet.getString(16));
                    client.setPensioner(resultSet.getBoolean(17));
                    client.setIncome(resultSet.getInt(18));
                    client.setMilitary(resultSet.getBoolean(19));

                    clients.add(client);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }
}
