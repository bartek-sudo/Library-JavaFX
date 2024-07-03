package com.example.library.model;

/**
 * Klasa reprezentująca obiekt książki w systemie bibliotecznym.
 */
public class Book {
    private final int id;
    private final String title;
    private final String author;
    private final String publisher;
    private final String status;
    private byte[] image;

    /**
     * Konstruktor inicjalizujący obiekt książki.
     *
     * @param id        Identyfikator książki.
     * @param title     Tytuł książki.
     * @param author    Autor książki.
     * @param publisher Wydawca książki.
     * @param status    Status książki (np. dostępna, wypożyczona).
     * @param image     Dane obrazu okładki książki w postaci tablicy bajtów.
     */
    public Book(int id, String title, String author, String publisher, String status, byte[] image) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.status = status;
        this.image = image;
    }

    /**
     * Pobiera tytuł książki.
     *
     * @return Tytuł książki.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Pobiera autora książki.
     *
     * @return Autor książki.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Pobiera identyfikator książki.
     *
     * @return Identyfikator książki.
     */
    public int getId() {
        return id;
    }

    /**
     * Pobiera wydawcę książki.
     *
     * @return Wydawca książki.
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Pobiera status książki.
     *
     * @return Status książki.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Pobiera dane obrazu okładki książki w postaci tablicy bajtów.
     *
     * @return Dane obrazu okładki książki.
     */
    public byte[] getImage() {
        return image;
    }
}
