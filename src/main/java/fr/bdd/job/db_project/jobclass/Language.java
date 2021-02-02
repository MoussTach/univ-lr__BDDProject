package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class Language {

    private final StringProperty language_ID = new SimpleStringProperty("n/a");
    private final StringProperty country = new SimpleStringProperty("n/a");

    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public Language() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link Language}
     */
    public Language(Language clone) {

        if (clone != null) {

            this.language_ID.set(clone.language_ID.get());

            this.country.set(clone.country.get());
        }
    }

    /**
     * Getter for the variable language_ID.
     *
     * @return {@link String} - return the variable language_ID.
     */
    public String getlanguage_ID() {
        return language_ID.get();
    }

    /**
     * Getter for the variable country.
     *
     * @return {@link String} - return the variable country.
     */
    public String getcountry() {
        return country.get();
    }

    /**
     * Setter for the variable language_ID.
     *
     * @param language_ID - {@link String} - index of the Language class.
     * @return - {@link Language} - builder pattern return
     */
    public Language setlanguage_ID(String language_ID) {
        this.language_ID.set(language_ID);
        return this;
    }

    /**
     * Setter for the variable country.
     *
     * @param country - {@link String} - country of this class.
     * @return - {@link Language} - builder pattern return
     */
    public Language setcountry(String country) {
        this.country.set(country);
        return this;
    }

    /**
     * Property of the variable language_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable language_ID.
     */
    public StringProperty language_IDProperty() {
        return language_ID;
    }

    /**
     * Property of the variable country.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable country.
     */
    public StringProperty countryProperty() {
        return country;
    }

    /**
     * ToString methods, created when this class is used on listing
     *
     * @return - {@link String} - the builded list
     */
    @Override
    public String toString() {
        return String.format("%s", language_ID.get());
    }
}
