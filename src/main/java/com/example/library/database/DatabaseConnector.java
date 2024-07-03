package com.example.library.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Klasa służąca do nawiązywania połączenia z bazą danych PostgreSQL.
 */
public class DatabaseConnector {
    private static final String URL = "jdbc:postgresql://localhost:5432/library";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    /**
     * Metoda zwracająca połączenie do bazy danych.
     *
     * @return Połączenie do bazy danych.
     * @throws SQLException W przypadku problemów z nawiązaniem połączenia.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
