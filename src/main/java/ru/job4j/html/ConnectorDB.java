package ru.job4j.html;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectorDB {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectorDB.class.getName());
    private Connection cnn;

    public Connection getConnection(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("driver-class-name"));
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
        try {
            this.cnn = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password")
            );
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        LOG.trace("The database connection was successful!");
        return this.cnn;
    }
}
