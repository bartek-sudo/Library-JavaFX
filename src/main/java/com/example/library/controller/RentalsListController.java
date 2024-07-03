package com.example.library.controller;

import com.example.library.Application;
import com.example.library.database.BookDAO;
import com.example.library.database.RentalDAO;
import com.example.library.model.Rental;
import com.example.library.model.RentalDetails;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Kontroler do obsługi widoku listy wypożyczeń w aplikacji bibliotecznej.
 */
public class RentalsListController {

    public TextField searchField;

    @FXML
    private TableView<RentalDetails> rentalsTableView;

    @FXML
    public TableColumn<Rental, Integer> idColumn;

    @FXML
    private TableColumn<Rental, Integer> bookIdColumn;

    @FXML
    private TableColumn<Rental, Integer> titleColumn;

    @FXML
    private TableColumn<Rental, Integer> authorColumn;

    @FXML
    private TableColumn<Rental, Integer> readerIdColumn;

    @FXML
    private TableColumn<Rental, Integer> nameColumn;

    @FXML
    private TableColumn<Rental, Integer> surnameColumn;

    @FXML
    private TableColumn<Rental, String> rentalDateColumn;

    @FXML
    private TableColumn<Rental, String> returnDateColumn;

    private RentalDAO rentalDAO;
    private int selectedBookId;
    private int selectedReaderId;
    private BookDAO bookDAO;

    /**
     * Konstruktor domyślny.
     */
    public RentalsListController() {
    }

    /**
     * Otwiera widok listy książek.
     */
    @FXML
    private void openBookListView() {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("BooksListView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) rentalsTableView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Otwiera widok listy czytelników.
     */
    @FXML
    private void openReadersListView() {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("ReadersListView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) rentalsTableView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Zamyka aplikację.
     */
    public void closeApp() {
        Stage stage = (Stage) rentalsTableView.getScene().getWindow();
        stage.close();
    }

    /**
     * Inicjalizuje kontroler ustawiając odpowiednie instancje DAO i inicjalizując kolumny tabeli.
     */
    @FXML
    public void initialize() {
        rentalDAO = new RentalDAO();
        bookDAO = new BookDAO();
        initializeColumns();
        updateRentalsList();
    }

    /**
     * Aktualizuje listę wypożyczeń w tabeli.
     */
    private void updateRentalsList() {
        rentalsTableView.getItems().setAll(rentalDAO.getAllRentals());
    }

    /**
     * Inicjalizuje kolumny tabeli.
     */
    private void initializeColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        readerIdColumn.setCellValueFactory(new PropertyValueFactory<>("reader_id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        rentalDateColumn.setCellValueFactory(new PropertyValueFactory<>("rental_date"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("return_date"));
    }

    /**
     * Zwraca wypożyczoną książkę.
     */
    public void returnRental() {
        RentalDetails selectedRental = rentalsTableView.getSelectionModel().getSelectedItem();
        if (selectedRental != null) {
            rentalDAO.returnRental(selectedRental.getId());
            bookDAO.updateBookStatus(selectedRental.getBook_id(), "dostępna");
            updateRentalsList();
        }
    }

    /**
     * Otwiera widok do wyboru książki.
     */
    @FXML
    public void openPickBook() {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("PickBookView.fxml"));
            Parent root = loader.load();

            // Pobierz kontroler po załadowaniu widoku
            PickBookController pickBookController = loader.getController();

            // Przekaż obecny kontroler jako parent
            pickBookController.setParentController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            openPickReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Otwiera widok do wyboru czytelnika.
     */
    public void openPickReader() {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("PickReaderView.fxml"));
            Parent root = loader.load();

            PickReaderController pickReaderController = loader.getController();
            pickReaderController.setParentController(this); // Przekazanie referencji

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            addRental();
            updateRentalsList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ustawia wybraną książkę.
     *
     * @param id Identyfikator książki.
     */
    public void setSelectedBook(int id) {
        selectedBookId = id;
    }

    /**
     * Ustawia wybranego czytelnika.
     *
     * @param id Identyfikator czytelnika.
     */
    public void setSelectedReader(int id) {
        selectedReaderId = id;
    }

    /**
     * Dodaje nowe wypożyczenie.
     */
    public void addRental() {
        rentalDAO.addRental(selectedBookId, selectedReaderId);
        bookDAO.updateBookStatus(selectedBookId, "wypożyczona");
        selectedBookId = 0;
        selectedReaderId = 0;
    }

    /**
     * Usuwa wybrane wypożyczenie.
     */
    public void deleteRental() {
        RentalDetails selectedRental = rentalsTableView.getSelectionModel().getSelectedItem();
        if (selectedRental != null) {
            rentalDAO.deleteRental(selectedRental.getId());
            bookDAO.updateBookStatus(selectedRental.getBook_id(), "dostępna");
            updateRentalsList();
        }
    }
}
