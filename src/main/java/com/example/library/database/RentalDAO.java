package com.example.library.database;

import com.example.library.model.RentalDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa dostępu do danych (DAO) dla obiektów wypożyczenia w bazie danych.
 */
public class RentalDAO {
    private Connection connection;

    /**
     * Konstruktor inicjalizujący połączenie z bazą danych.
     */
    public RentalDAO() {
        try {
            this.connection = DatabaseConnector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pobiera listę wszystkich wypożyczeń z bazy danych wraz z danymi o książce i czytelniku.
     *
     * @return Lista wszystkich wypożyczeń z danymi szczegółowymi.
     */
    public List<RentalDetails> getAllRentals() {
        List<RentalDetails> rentals = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT rentals.rental_id, rentals.book_id, books.title, books.author, " +
                            "rentals.reader_id, readers.name, readers.surname, rentals.rental_date, rentals.return_date " +
                            "FROM rentals, books, readers " +
                            "WHERE rentals.book_id = books.book_id AND rentals.reader_id = readers.reader_id");

            while (resultSet.next()) {
                RentalDetails rentalDetails = new RentalDetails(
                        resultSet.getInt("rental_id"),
                        resultSet.getInt("book_id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("reader_id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getDate("rental_date"),
                        resultSet.getDate("return_date")
                );
                rentals.add(rentalDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }

    /**
     * Dodaje nowe wypożyczenie do bazy danych.
     *
     * @param selectedBookId   Identyfikator wybranej książki do wypożyczenia.
     * @param selectedReaderId Identyfikator wybranego czytelnika.
     */
    public void addRental(int selectedBookId, int selectedReaderId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO rentals (book_id, reader_id, rental_date, return_date) VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, selectedBookId);
            preparedStatement.setInt(2, selectedReaderId);
            preparedStatement.setDate(3, new Date(System.currentTimeMillis()));
            preparedStatement.setDate(4, null);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Oznacza zakończenie wypożyczenia poprzez ustawienie daty zwrotu.
     *
     * @param id Identyfikator wypożyczenia do zakończenia.
     */
    public void returnRental(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE rentals SET return_date = ? WHERE rental_id = ?");
            preparedStatement.setDate(1, new Date(System.currentTimeMillis()));
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Usuwa wybrane wypożyczenie z bazy danych.
     *
     * @param id Identyfikator wypożyczenia do usunięcia.
     */
    public void deleteRental(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM rentals WHERE rental_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
