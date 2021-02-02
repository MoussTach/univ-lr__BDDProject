package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class Document_Publication {

    private final StringProperty document_ID = new SimpleStringProperty("n/a");
    private final IntegerProperty publication_ID = new SimpleIntegerProperty(0);
    private final StringProperty language_ID = new SimpleStringProperty("n/a");

    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public Document_Publication() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link Document_Publication}
     */
    public Document_Publication(Document_Publication clone) {

        if (clone != null) {

            this.document_ID.set(clone.document_ID.get());

            this.publication_ID.set(clone.publication_ID.get());
            this.language_ID.set(clone.language_ID.get());
        }
    }

    /**
     * Getter for the variable document_ID.
     *
     * @return {@link String} - return the variable document_ID.
     */
    public String getdocument_ID() {
        return document_ID.get();
    }

    /**
     * Getter for the variable publication_ID.
     *
     * @return {@link Integer} - return the variable publication_ID.
     */
    public Integer getpublication_ID() {
        return publication_ID.get();
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
     * Setter for the variable document_ID.
     *
     * @param document_ID - {@link String} - index of the Document_Publication class.
     * @return - {@link Document_Publication} - builder pattern return
     */
    public Document_Publication setdocument_ID(String document_ID) {
        this.document_ID.set(document_ID);
        return this;
    }

    /**
     * Setter for the variable publication_ID.
     *
     * @param publication_ID - {@link Integer} - publication_ID of this class.
     * @return - {@link Document_Publication} - builder pattern return
     */
    public Document_Publication setpublication_ID(Integer publication_ID) {
        this.publication_ID.set(publication_ID);
        return this;
    }

    /**
     * Setter for the variable language_ID.
     *
     * @param language_ID - {@link String} - language_ID of this class.
     * @return - {@link Document_Publication} - builder pattern return
     */
    public Document_Publication setlanguage_ID(String language_ID) {
        this.language_ID.set(language_ID);
        return this;
    }

    /**
     * Property of the variable document_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable document_ID.
     */
    public StringProperty document_IDProperty() {
        return document_ID;
    }

    /**
     * Property of the variable publication_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link IntegerProperty} - return the property of the variable publication_ID.
     */
    public IntegerProperty publication_IDProperty() {
        return publication_ID;
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
     * ToString methods, created when this class is used on listing
     *
     * @return - {@link String} - the builded list
     */
    @Override
    public String toString() {
        return String.format("%s", document_ID.get());
    }
}
