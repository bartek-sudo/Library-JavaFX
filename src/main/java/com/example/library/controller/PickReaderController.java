package com.example.library.controller;

import com.example.library.database.ReaderDAO;
import com.example.library.model.Reader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Kontroler do wybierania czytelnika w aplikacji bibliotecznej.
 */
public class PickReaderController {
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
    public void initialize() {
        readerDAO = new ReaderDAO();
        initializeColumns();
        updateReadersList();
    }

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
                showAlert("Nie znaleziono czytelnika", "Nie znaleziono czytelnika o podanym imieniu lub nazwisku");
            }
        } else {
            // Pole wyszukiwania jest puste - zaktualizuj tabelę, aby pokazać wszystkich czytelników
            updateReadersList();
        }
    }

    /**
     * Dodaje wypożyczenie dla wybranego czytelnika.
     */
    public void addRental() {
        Reader selectedReader = readersTableView.getSelectionModel().getSelectedItem();
        if (selectedReader != null) {
            parentController.setSelectedReader(selectedReader.getId());
            closeStage();
        } else {
            showAlert("Nie wybrano czytelnika", "Wybierz czytelnika, któremu chcesz wypożyczyć książkę");
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
        Stage stage = (Stage) readersTableView.getScene().getWindow();
        stage.close();
    }
}
