package com.example.library.controller;

import com.example.library.database.BookDAO;
import com.example.library.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Kontroler do obsługi dodawania nowej książki w aplikacji bibliotecznej.
 */
public class AddBookController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField publisherField;

    @FXML
    private Text imagePathField;
    private BookDAO bookDAO;

    /**
     * Inicjalizuje kontroler, tworząc instancję BookDAO.
     */
    @FXML
    public void initialize() {
        bookDAO = new BookDAO();
    }

    private BooksListController mainController;

    /**
     * Ustawia główny kontroler do komunikacji między kontrolerami.
     *
     * @param mainController Główna instancja kontrolera.
     */
    public void setMainController(BooksListController mainController) {
        this.mainController = mainController;
    }

    /**
     * Dodaje nową książkę na podstawie wprowadzonych danych. Dodaje książkę do bazy danych,
     * aktualizuje listę książek w głównym kontrolerze i zamyka okno, jeżeli dane są poprawne.
     *
     * @throws IOException Jeśli wystąpi problem z odczytem pliku graficznego.
     */
    @FXML
    public void addBook() throws IOException {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String publisher = publisherField.getText().trim();
        String imagePath = imagePathField.getText().trim();
        byte[] imageBytes = readImageFromFile(imagePath);

        if (!title.isEmpty() && !author.isEmpty()) {
            Book newBook = new Book(0, title, author, publisher, "dostępna", imageBytes);
            // Utwórz nowy obiekt książki
            bookDAO.addBook(newBook); // Dodaj książkę do bazy danych
            if (mainController != null) {
                mainController.updateBookList();
            }
            closeStage();
        }
    }

    /**
     * Odczytuje dane obrazu z pliku.
     *
     * @param imagePath Ścieżka do pliku obrazu.
     * @return Tablica bajtów reprezentująca obraz.
     * @throws IOException Jeśli wystąpi problem z odczytem pliku.
     */
    private byte[] readImageFromFile(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);
        return Files.readAllBytes(path);
    }

    /**
     * Zamyka aktualne okno dialogowe.
     */
    private void closeStage() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    /**
     * Otwiera okno wyboru pliku graficznego i aktualizuje ścieżkę do pliku w polu tekstowym.
     */
    @FXML
    private void browseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp")
        );

        Stage stage = (Stage) titleField.getScene().getWindow();
        java.io.File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            imagePathField.setText(selectedFile.getAbsolutePath());
        }
    }
}
