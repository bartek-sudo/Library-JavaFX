package com.example.library.controller;

import com.example.library.Application;
import com.example.library.database.ReaderDAO;
import com.example.library.model.Reader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Kontroler listy czytelników w aplikacji bibliotecznej.
 */
public class ReadersListController {
    @FXML
    private TableView<Reader> readersTableView;

    @FXML
    public TableColumn<Reader, Integer> idColumn;

    @FXML
    private TableColumn<Reader, String> nameColumn;

    @FXML
    private TableColumn<Reader, String> surnameColumn;

    @FXML
    private TableColumn<Reader, String> emailColumn;

    @FXML
    private TableColumn<Reader, String> phoneColumn;

    @FXML
    private TableColumn<Reader, LocalDateTime> registrationDateColumn;

    private ReaderDAO readerDAO;

    @FXML
    private TextField searchField;

    /**
     * Otwiera widok listy książek.
     */
    @FXML
    private void openBookListView() {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("BooksListView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) readersTableView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Otwiera widok listy wypożyczeń.
     */
    @FXML
    private void openRentalsListView() {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("RentalsListView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) readersTableView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wyświetla okno dialogowe potwierdzające usunięcie czytelnika.
     */
    private boolean showDeleteConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie usunięcia");
        alert.setHeaderText(null);
        alert.setContentText("Czy na pewno chcesz usunąć tego czytelnika?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    @FXML
    private void initialize() {
        readerDAO = new ReaderDAO();
        initializeColumns();
        updateReadersList();
    }

    /**
     * Inicjalizuje kolumny w tabeli.
     */
    private void initializeColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        registrationDateColumn.setCellValueFactory(new PropertyValueFactory<>("registration_date"));
    }

    /**
     * Aktualizuje listę czytelników w tabeli.
     */
    protected void updateReadersList() {
        readersTableView.getItems().setAll(readerDAO.getAllReaders());
    }

    /**
     * Zamyka aplikację.
     */
    public void closeApp() {
        Stage stage = (Stage) readersTableView.getScene().getWindow();
        stage.close();
    }

    /**
     * Wyszukuje czytelników na podstawie podanego imienia lub nazwiska.
     */
    public void searchReaders() {
        String searchTerm = searchField.getText();

        if (!searchTerm.isEmpty()) {
            List<Reader> foundReaders = readerDAO.searchReadersByName(searchTerm);

            if (!foundReaders.isEmpty()) {
                // Znaleziono czytelników - zaktualizuj tabelę
                readersTableView.getItems().setAll(foundReaders);
            } else {
                showAlert("Brak wyników", "Nie znaleziono czytelników o podanym imieniu lub nazwisku");
            }
        } else {
            // Pole wyszukiwania jest puste - zaktualizuj tabelę, aby pokazać wszystkich czytelników
            updateReadersList();
        }
    }

    /**
     * Otwiera okno dialogowe do dodawania czytelnika.
     */
    public void openAddReaderDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("AddReaderDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(readersTableView.getScene().getWindow());
            dialogStage.setScene(new Scene(root));

            AddReaderController addReaderController = loader.getController();
            // Przekazanie referencji do głównego kontrolera, aby mógł odświeżyć widok po dodaniu czytelnika
            addReaderController.setMainController(this);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Usuwa wybranego czytelnika.
     */
    public void deleteSelectedReader() {
        Reader selectedReader = readersTableView.getSelectionModel().getSelectedItem();

        if (selectedReader != null) {
            boolean confirmation = showDeleteConfirmationDialog();
            if (confirmation) {
                readerDAO.deleteReader(selectedReader);
                updateReadersList();
            }
        } else {
            showAlert("Nie wybrano czytelnika", "Proszę wybrać czytelnika do usunięcia");
        }
    }

    /**
     * Wyświetla alert z błędem.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
