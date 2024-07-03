package com.example.library.controller;

import com.example.library.Application;
import javafx.fxml.FXML;
import com.example.library.database.BookDAO;
import com.example.library.model.Book;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Kontroler do obsługi listy książek w aplikacji bibliotecznej.
 */
public class BooksListController {
    @FXML
    private TableView<Book> bookTableView;

    @FXML
    private TableColumn<Book, Integer> idColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> publisherColumn;

    @FXML
    private TableColumn<Book, String> statusColumn;

    @FXML
    private ImageView coverImageColumn;

    private BookDAO bookDAO;

    @FXML
    private void openReadersListView() {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("ReadersListView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) bookTableView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openRentalsListView() {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("RentalsListView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) bookTableView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openAddBookDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("AddBookDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(bookTableView.getScene().getWindow());
            dialogStage.setScene(new Scene(root));

            AddBookController addBookController = loader.getController();
            // Przekazanie referencji do głównego kontrolera, aby mógł odświeżyć widok po dodaniu książki
            addBookController.setMainController(this);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteSelectedBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            boolean confirmation = showDeleteConfirmationDialog();
            if (confirmation) {
                bookDAO.deleteBook(selectedBook);
                updateBookList();
            }
        } else {
            showAlert("Nie wybrano książki", "Wybierz książkę do usunięcia");
        }
    }

    private boolean showDeleteConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie usunięcia");
        alert.setHeaderText(null);
        alert.setContentText("Czy na pewno chcesz usunąć wybraną książkę?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    @FXML
    private TextField searchField;

    @FXML
    private void searchBooks() {
        String searchTerm = searchField.getText();

        if (!searchTerm.isEmpty()) {
            List<Book> foundBooks = bookDAO.searchBooksByTitle(searchTerm);

            if (!foundBooks.isEmpty()) {
                // Znaleziono książki - zaktualizuj tabelę
                bookTableView.getItems().setAll(foundBooks);
            } else {
                showAlert("Brak wyników", "Nie znaleziono książek dla podanego tytułu");
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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    protected void updateBookList() {
        bookTableView.getItems().setAll(bookDAO.getAllBooks());
        bookTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Wyświetl okładkę wybranej książki w ImageView
                displayBookCover(newSelection.getImage());
            }
        });
    }

    private void displayBookCover(byte[] imageBytes) {
        if (imageBytes != null) {
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                Image image = new Image(bis);
                coverImageColumn.setImage(image);
            } catch (Exception e) {
                // Obsłuż błąd ładowania obrazka
                e.printStackTrace();
            }
        } else {
            // Obsłuż brak obrazka
            coverImageColumn.setImage(null);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void closeApp() {
        Stage stage = (Stage) bookTableView.getScene().getWindow();
        stage.close();
    }
}
