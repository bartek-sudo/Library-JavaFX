package com.example.library.model;

import java.sql.Date;

/**
 * Klasa reprezentująca obiekt wypożyczenia w systemie bibliotecznym.
 */
public class Rental {
    private final int id;
    private final int book_id;
    private final int reader_id;
    private final Date rental_date;
    private final Date return_date;

    /**
     * Konstruktor inicjalizujący obiekt wypożyczenia.
     *
     * @param id         Identyfikator wypożyczenia.
     * @param bookId     Identyfikator książki wypożyczonej.
     * @param readerId   Identyfikator czytelnika wypożyczającego.
     * @param rentalDate Data rozpoczęcia wypożyczenia.
     * @param returnDate Planowana data zwrotu wypożyczenia.
     */
    public Rental(int id, int bookId, int readerId, Date rentalDate, Date returnDate) {
        this.id = id;
        this.book_id = bookId;
        this.reader_id = readerId;
        this.rental_date = rentalDate;
        this.return_date = returnDate;
    }

    /**
     * Pobiera identyfikator wypożyczenia.
     *
     * @return Identyfikator wypożyczenia.
     */
    public int getId() {
        return id;
    }
}
