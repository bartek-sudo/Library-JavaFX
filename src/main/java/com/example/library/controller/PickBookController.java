package com.example.library.controller;

import com.example.library.database.BookDAO;
import com.example.library.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

/**
 * Kontroler do wybierania książki w aplikacji bibliotecznej.
 */
public class PickBookController {
    @FXML
    private TableView<Book> bookTableView;

    @FXML
    public TableColumn<Book, Integer> bookIdColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    private BookDAO bookDAO;

    @FXML
    private TextField searchField;

    private RentalsListController parentController;

    /**
     * Ustawia kontroler rodzica do komunikacji między kontrolerami.
     *
     * @param parentController Kontroler rodzica.
     */
    public void setParentController(RentalsListController parentController) {
        this.parentController = parentController;
    }

    @FXML
    private void searchBooks() {
        String searchTerm = searchField.getText();

        if (!searchTerm.isEmpty()) {
            List<Book> foundBooks = bookDAO.searchBooksByTitle(searchTerm);

            if (!foundBooks.isEmpty()) {
                // Znaleziono książki - zaktualizuj tabelę
                bookTableView.getItems().setAll(foundBooks);
            } else {
                showAlert("Nie znaleziono książek", "Nie znaleziono książek o podanym tytule");
            }
        } else {
            // Pole wyszukiwania jest puste - zaktualizuj tabelę, aby pokazać wszystkie książki
            updateBookList();
        }
    }

    @FXML
    public void initialize() {
        bookDAO = new BookDAO();
        initializeColumns();
        updateBookList();
    }

    private void initializeColumns() {
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        // Ustaw inne kolumny podobnie
    }

    protected void updateBookList() {
        bookTableView.getItems().setAll(bookDAO.getAvailableBooks());
    }

    /**
     * Otwiera widok wyboru czytelnika po wybraniu książki.
     */
    public void openPickReader() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();

        // Sprawdź, czy coś zostało wybrane
        if (selectedBook != null) {
            // Przekaż wybraną książkę do RentalsListController
            parentController.setSelectedBook(selectedBook.getId());
            closeStage();

        } else {
            showAlert("Nie wybrano książki", "Nie wybrano żadnej książki");
        }
    }

    /**
     * Wyświetla alert z błędem.
     *
     * @param title   Tytuł alertu.
     * @param message Treść alertu.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Zamyka aktualne okno dialogowe.
     */
    private void closeStage() {
        Stage stage = (Stage) bookTableView.getScene().getWindow();
        stage.close();
    }
}
