package com.example.library.model;

import java.sql.Date;

/**
 * Klasa reprezentująca obiekt czytelnika w systemie bibliotecznym.
 */
public class Reader {
    private final int id;
    private final String name;
    private final String surname;
    private final String email;
    private final String phone_number;
    private final Date registration_date;

    /**
     * Konstruktor inicjalizujący obiekt czytelnika.
     *
     * @param id              Identyfikator czytelnika.
     * @param name            Imię czytelnika.
     * @param surname         Nazwisko czytelnika.
     * @param email           Adres e-mail czytelnika.
     * @param phoneNumber     Numer telefonu czytelnika.
     * @param registrationDate Data rejestracji czytelnika.
     */
    public Reader(int id, String name, String surname, String email, String phoneNumber, Date registrationDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone_number = phoneNumber;
        this.registration_date = registrationDate;
    }

    /**
     * Pobiera identyfikator czytelnika.
     *
     * @return Identyfikator czytelnika.
     */
    public int getId() {
        return id;
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
     * Pobiera adres e-mail czytelnika.
     *
     * @return Adres e-mail czytelnika.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Pobiera numer telefonu czytelnika.
     *
     * @return Numer telefonu czytelnika.
     */
    public String getPhone_number() {
        return phone_number;
    }

    /**
     * Pobiera datę rejestracji czytelnika.
     *
     * @return Data rejestracji czytelnika.
     */
    public Date getRegistration_date() {
        return registration_date;
    }
}
