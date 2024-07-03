package com.example.library.controller;

import com.example.library.database.ReaderDAO;
import com.example.library.model.Reader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Kontroler do obsługi dodawania nowego czytelnika w aplikacji bibliotecznej.
 */
public class AddReaderController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    private ReaderDAO readerDAO;

    /**
     * Inicjalizuje kontroler, tworząc instancję ReaderDAO.
     */
    @FXML
    public void initialize() {
        readerDAO = new ReaderDAO();
    }

    private ReadersListController mainController;

    /**
     * Ustawia główny kontroler do komunikacji między kontrolerami.
     *
     * @param mainController Główna instancja kontrolera.
     */
    public void setMainController(ReadersListController mainController) {
        this.mainController = mainController;
    }

    /**
     * Zamyka aktualne okno dialogowe.
     */
    private void closeStage() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    /**
     * Dodaje nowego czytelnika na podstawie wprowadzonych danych. Dodaje czytelnika do bazy danych,
     * aktualizuje listę czytelników w głównym kontrolerze i zamyka okno, jeżeli dane są poprawne.
     */
    public void addReader() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        if (isValidInput(name, surname, email, phone)) {
            phone = phone.substring(0, 3) + "-" + phone.substring(3, 6) + "-" + phone.substring(6, 9);
            Reader newReader = new Reader(0, name, surname, email, phone, Date.valueOf(LocalDate.now()));
            // Utwórz nowy obiekt czytelnika
            readerDAO.addReader(newReader); // Dodaj czytelnika do bazy danych

            if (mainController != null) {
                mainController.updateReadersList();
            }

            closeStage();
        }
    }

    /**
     * Sprawdza poprawność wprowadzonych danych.
     *
     * @param name    Imię czytelnika.
     * @param surname Nazwisko czytelnika.
     * @param email   Adres e-mail czytelnika.
     * @param phone   Numer telefonu czytelnika.
     * @return true, jeżeli dane są poprawne; false w przeciwnym przypadku.
     */
    private boolean isValidInput(String name, String surname, String email, String phone) {
        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showAlert("Błąd", "Wszystkie pola muszą być wypełnione.");
            return false;
        }

        // Walidacja adresu e-mail
        if (!email.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")) {
            showAlert("Błąd", "Niepoprawny adres e-mail.");
            return false;
        }

        // Walidacja numeru telefonu
        if (!phone.matches("\\d{9}")) {
            showAlert("Błąd", "Niepoprawny numer telefonu.");
            return false;
        }

        return true;
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
}
