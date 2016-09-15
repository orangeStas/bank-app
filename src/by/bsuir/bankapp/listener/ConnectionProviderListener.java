package by.bsuir.bankapp.listener;

import by.bsuir.bankapp.dao.connectionpool.ConnectionPoolImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConnectionProviderListener implements ServletContextListener {
    private ConnectionPoolImpl connectionPool;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        connectionPool = ConnectionPoolImpl.getInstance();
        connectionPool.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (connectionPool != null) {
            connectionPool.dispose();
        }
    }
}
