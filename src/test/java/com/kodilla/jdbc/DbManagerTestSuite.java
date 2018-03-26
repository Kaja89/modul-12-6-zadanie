package com.kodilla.jdbc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManagerTestSuite {
    private static DbManager dbManager;
    private static Statement statement;
    private static ResultSet resultSet;

    @Before
    public void initializeDbManager() throws SQLException {
        dbManager = DbManager.getInstance();
    }

    @Before
    public void initializeStatement() throws SQLException {
        statement = dbManager.getConnection().createStatement();
    }

    @After
    public void closeResultSet() throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
    }

    @After
    public void closeStatement() throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    @Test
    public void testConnect() {
        //Given
        //When
        //Then
        Assert.assertNotNull(dbManager.getConnection());
    }

    @Test
    public void testSelectUsers() throws SQLException {
        //Given
        String sqlQuery = "SELECT * FROM USERS";
        //When
        resultSet = statement.executeQuery(sqlQuery);
        //Then
        int counter = 0;
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("ID") + ", " + resultSet.getString("FIRSTNAME") + ", " + resultSet.getString("LASTNAME"));
            counter++;
        }
        Assert.assertEquals(5, counter);
    }

    @Test
    public void testSelectUsersAndPost() throws SQLException {
        //Given
        String sqlQuery = "SELECT U.FIRSTNAME, U.LASTNAME, COUNT(*) AS POSTS_NUMBER\n" +
                "FROM USERS U, POSTS P\n" +
                "WHERE U.ID = P.USER_ID\n" +
                "GROUP BY USER_ID\n" +
                "HAVING COUNT(*) > 1;";
        //When
        resultSet = statement.executeQuery(sqlQuery);
        //Then
        int counter = 0;
        while (resultSet.next()) {
            System.out.println(resultSet.getString("FIRSTNAME") + ", " + resultSet.getString("LASTNAME"));
            counter++;
        }
        Assert.assertEquals(2, counter);
    }
}
