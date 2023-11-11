package project_one;

import java.sql.*;
import java.util.Scanner;

public class ProjectOne {
    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/project_one";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "0000";


    public ProjectOne() {
        try {
            // establish connection to the database
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    // create the users table
    public void createTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS " +
                "users(name TEXT, email TEXT, age INTEGER, location TEXT, designation TEXT)";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTable);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            closeConnection();
        }

    }

    // Populate the users table
    public int populateTable() {
        int count = 1;

        String populateUsers = "INSERT INTO users(name, email, age, location, designation) VALUES(?, ?, ?, ?, ?)";
        String getUser = "SELECT * FROM users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(populateUsers);
             Scanner input = new Scanner(System.in)) {

            ResultSetMetaData resultData = connection.prepareStatement(getUser).getMetaData();

            do {

                System.out.printf("--------------- user info %d ---------------%n", count);

                // Loop to collect users details
                for (int i = 1; i <= resultData.getColumnCount(); i++) {
                    System.out.print("Enter " + resultData.getColumnName(i) + ": ");

                    if(i == 3) {
                        preparedStatement.setInt(i, input.nextInt());
                        input.nextLine();
                    } else {
                        preparedStatement.setString(i, input.nextLine());
                    }
                }
                preparedStatement.execute();
                System.out.println();

                count++;
            } while (count <= 10);



        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            closeConnection();
        }
        return count;
    }

    // Close connection
    private void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Main method
    public static void main(String[] args) throws SQLException {
        ProjectOne projectOne = new ProjectOne();

        projectOne.createTable();
        projectOne.populateTable();
    }
}


