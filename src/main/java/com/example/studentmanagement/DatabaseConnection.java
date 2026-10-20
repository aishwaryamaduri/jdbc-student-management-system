package com.example.studentmanagement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
    private static final String DATABASE_URL = "jdbc:h2:file:./data/student_management";

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            Files.createDirectories(Path.of("data"));
        } catch (IOException exception) {
            throw new SQLException("Could not create the H2 database directory.", exception);
        }

        return DriverManager.getConnection(DATABASE_URL);
    }
}
