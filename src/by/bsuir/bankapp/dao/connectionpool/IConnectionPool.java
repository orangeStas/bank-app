package by.bsuir.bankapp.dao.connectionpool;

import java.sql.Connection;

public interface IConnectionPool {

    void init();

    void dispose();

    Connection getConnection();

    interface IPooledConnection extends Connection{}
}
