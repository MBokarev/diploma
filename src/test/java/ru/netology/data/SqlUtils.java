package ru.netology.data;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlUtils {

    public SqlUtils() {
    }

    @Value
    public static class Status {
        String status;
    }

    private static Connection getConnection() {
        String url = System.getProperty("db.url");
        String user = System.getProperty("db.user");
        String password = System.getProperty("db.password");
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getStatusDebitPurchase() {
        val dataSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        val runner = new QueryRunner();
        try (val connection = getConnection()) {
            return runner.query(connection, dataSQL, new ScalarHandler<String>());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
    public static String getStatusCreditPurchase() {
        val dataSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        val runner = new QueryRunner();
        try (val connection = getConnection()) {
            return runner.query(connection, dataSQL, new ScalarHandler<String>());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
    public static Long getRowsDebitPurchase() {
        val getRows = "SELECT COUNT(*) FROM payment_entity";
        QueryRunner runner = new QueryRunner();
        try (val connection = getConnection()) {
            val rowsCount = runner.query(connection, getRows, new ScalarHandler<Long>());
            return rowsCount;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static Long getRowsCreditPurchase() {
        val getRows = "SELECT COUNT(*) FROM credit_request_entity";
        QueryRunner runner = new QueryRunner();
        try (val connection = getConnection()) {
            val rowsCount = runner.query(connection, getRows, new ScalarHandler<Long>());
            return rowsCount;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static void clearTables() {
        val cleanCredit = "DELETE FROM credit_request_entity;";
        val cleanOrder = "DELETE FROM order_entity;";
        val cleanPayment = "DELETE FROM payment_entity";
        val runner = new QueryRunner();
        try (val connection = getConnection()) {
            runner.update(connection, cleanCredit);
            runner.update(connection, cleanOrder);
            runner.update(connection, cleanPayment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}