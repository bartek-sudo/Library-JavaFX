package com.example.library.database;

import com.example.library.model.Reader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa dostępu do danych (DAO) dla obiektów czytelnika w bazie danych.
 */
public class ReaderDAO {
    private Connection connection;

    /**
     * Konstruktor inicjalizujący połączenie z bazą danych.
     */
    public ReaderDAO() {
        try {
            this.connection = DatabaseConnector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pobiera listę wszystkich czytelników z bazy danych.
     *
     * @return Lista wszystkich czytelników.
     */
    public List<Reader> getAllReaders() {
        List<Reader> readers = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM readers");
            while (resultSet.next()) {
                Reader reader = new Reader(
                        resultSet.getInt("reader_id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getDate("registration_date"));
                readers.add(reader);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readers;
    }

    /**
     * Wyszukuje czytelników na podstawie imienia lub nazwiska.
     *
     * @param searchTerm Wyszukiwany fragment imienia lub nazwiska.
     * @return Lista znalezionych czytelników.
     */
    public List<Reader> searchReadersByName(String searchTerm) {
        List<Reader> foundReaders = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM readers WHERE name LIKE ? OR surname LIKE ?");
            preparedStatement.setString(1, "%" + searchTerm + "%");
            preparedStatement.setString(2, "%" + searchTerm + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Reader reader = new Reader(
                        resultSet.getInt("reader_id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getDate("registration_date"));
                foundReaders.add(reader);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundReaders;
    }

    /**
     * Dodaje nowego czytelnika do bazy danych.
     *
     * @param newReader Nowy czytelnik do dodania.
     */
    public void addReader(Reader newReader) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO readers (name, surname, email, phone_number, registration_date) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, newReader.getName());
            preparedStatement.setString(2, newReader.getSurname());
            preparedStatement.setString(3, newReader.getEmail());
            preparedStatement.setString(4, newReader.getPhone_number());
            preparedStatement.setDate(5, newReader.getRegistration_date());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Usuwa wybranego czytelnika z bazy danych.
     *
     * @param selectedReader Czytelnik do usunięcia.
     */
    public void deleteReader(Reader selectedReader) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM readers WHERE reader_id = ?");
            preparedStatement.setInt(1, selectedReader.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
