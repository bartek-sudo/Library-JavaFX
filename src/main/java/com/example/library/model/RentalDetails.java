package com.example.library.model;

import java.sql.Date;

/**
 * Klasa reprezentująca szczegóły wypożyczenia w systemie bibliotecznym.
 */
public class RentalDetails {
    private int id;
    private int book_id;
    private String title;
    private String author;
    private int reader_id;
    private String name;
    private String surname;
    private Date rental_date;
    private Date return_date;

    /**
     * Konstruktor inicjalizujący obiekt szczegółów wypożyczenia.
     *
     * @param id           Identyfikator wypożyczenia.
     * @param book_id      Identyfikator książki wypożyczonej.
     * @param title        Tytuł książki wypożyczonej.
     * @param author       Autor książki wypożyczonej.
     * @param reader_id    Identyfikator czytelnika wypożyczającego.
     * @param name         Imię czytelnika.
     * @param surname      Nazwisko czytelnika.
     * @param rental_date  Data rozpoczęcia wypożyczenia.
     * @param return_date  Planowana data zwrotu wypożyczenia.
     */
    public RentalDetails(int id, int book_id, String title, String author, int reader_id,
                         String name, String surname, Date rental_date, Date return_date) {
        this.id = id;
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.reader_id = reader_id;
        this.name = name;
        this.surname = surname;
        this.rental_date = rental_date;
        this.return_date = return_date;
    }

    /**
     * Pobiera identyfikator wypożyczenia.
     *
     * @return Identyfikator wypożyczenia.
     */
    public int getId() {
        return id;
    }

    /**
     * Pobiera identyfikator książki wypożyczonej.
     *
     * @return Identyfikator książki wypożyczonej.
     */
    public int getBook_id() {
        return book_id;
    }

    /**
     * Pobiera tytuł książki wypożyczonej.
     *
     * @return Tytuł książki wypożyczonej.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Pobiera autora książki wypożyczonej.
     *
     * @return Autor książki wypożyczonej.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Pobiera identyfikator czytelnika wypożyczającego.
     *
     * @return Identyfikator czytelnika wypożyczającego.
     */
    public int getReader_id() {
        return reader_id;
    }

    /**
     * Pobiera imię czytelnika.
     *
     * @return Imię czytelnika.
     */
    public String getName() {
        return name;
    }

    /**
     * Pobiera nazwisko czytelnika.
     *
     * @return Nazwisko czytelnika.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Pobiera datę rozpoczęcia wypożyczenia.
     *
     * @return Data rozpoczęcia wypożyczenia.
     */
    public Date getRental_date() {
        return rental_date;
    }

    /**
     * Pobiera planowaną datę zwrotu wypożyczenia.
     *
     * @return Planowana data zwrotu wypożyczenia.
     */
    public Date getReturn_date() {
        return return_date;
    }
}
