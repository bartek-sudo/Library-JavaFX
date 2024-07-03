package com.example.library.database;

import com.example.library.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa dostępu do danych (DAO) dla obiektów książki w bazie danych.
 */
public class BookDAO {
    private Connection connection;

    /**
     * Konstruktor inicjalizujący połączenie z bazą danych.
     */
    public BookDAO() {
        try {
            this.connection = DatabaseConnector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pobiera listę wszystkich książek z bazy danych.
     *
     * @return Lista wszystkich książek.
     */
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books");
            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("book_id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("publisher"),
                        resultSet.getString("status"),
                        resultSet.getBytes("cover_image"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Obsługa błędów
        }
        return books;
    }

    /**
     * Dodaje nową książkę do bazy danych.
     *
     * @param newBook Nowa książka do dodania.
     */
    public void addBook(Book newBook) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO books (title, author, publisher, status, cover_image) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, newBook.getTitle());
            preparedStatement.setString(2, newBook.getAuthor());
            preparedStatement.setString(3, newBook.getPublisher());
            preparedStatement.setString(4, newBook.getStatus());
            preparedStatement.setBytes(5, newBook.getImage());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Obsługa błędów
        }
    }

    /**
     * Usuwa wybraną książkę z bazy danych.
     *
     * @param selectedBook Książka do usunięcia.
     */
    public void deleteBook(Book selectedBook) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM books WHERE book_id = ?");
            preparedStatement.setInt(1, selectedBook.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wyszukuje książki na podstawie tytułu lub autora.
     *
     * @param searchTerm Wyszukiwany fragment tytułu lub autora.
     * @return Lista znalezionych książek.
     */
    public List<Book> searchBooksByTitle(String searchTerm) {
        List<Book> foundBooks = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?");
            preparedStatement.setString(1, "%" + searchTerm + "%");
            preparedStatement.setString(2, "%" + searchTerm + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("book_id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("publisher"),
                        resultSet.getString("status"),
                        resultSet.getBytes("cover_image"));
                foundBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundBooks;
    }

    /**
     * Aktualizuje status wypożyczenia dla wybranej książki.
     *
     * @param bookId Identyfikator książki.
     * @param status Nowy status książki.
     */
    public void updateBookStatus(int bookId, String status) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE books SET status = ? WHERE book_id = ?");
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pobiera listę dostępnych książek z bazy danych.
     *
     * @return Lista dostępnych książek.
     */
    public List<Book> getAvailableBooks() {
        List<Book> books = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books WHERE status = 'dostępna'");
            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("book_id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("publisher"),
                        resultSet.getString("status"),
                        resultSet.getBytes("cover_image"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
