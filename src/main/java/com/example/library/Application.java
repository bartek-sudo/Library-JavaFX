package com.example.library;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Klasa reprezentująca główną aplikację biblioteczną.
 */
public class Application extends javafx.application.Application {

    /**
     * Metoda startująca aplikację.
     *
     * @param stage Główny etap aplikacji.
     * @throws IOException Wyjątek w przypadku problemów z załadowaniem pliku FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("BooksListView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Library App");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metoda uruchamiająca aplikację.
     *
     * @param args Argumenty uruchomieniowe.
     */
    public static void main(String[] args) {
        launch();
    }
}
