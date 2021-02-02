package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;

import java.util.Date;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class Document_Author {

    private final StringProperty document_ID = new SimpleStringProperty("n/a");
    private final IntegerProperty category_ID = new SimpleIntegerProperty(0);

    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public Document_Author() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link Document_Author}
     */
    public Document_Author(Document_Author clone) {

        if (clone != null) {

            this.document_ID.set(clone.document_ID.get());

            this.category_ID.set(clone.category_ID.get());
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
     * Getter for the variable category_ID.
     *
     * @return {@link Integer} - return the variable category_ID.
     */
    public Integer getcategory_ID() {
        return category_ID.get();
    }

    /**
     * Setter for the variable document_ID.
     *
     * @param document_ID - {@link String} - index of the Document_Author class.
     * @return - {@link Document_Author} - builder pattern return
     */
    public Document_Author setdocument_ID(String document_ID) {
        this.document_ID.set(document_ID);
        return this;
    }

    /**
     * Setter for the variable category_ID.
     *
     * @param category_ID - {@link Integer} - category_ID of this class.
     * @return - {@link Document_Author} - builder pattern return
     */
    public Document_Author setcategory_ID(Integer category_ID) {
        this.category_ID.set(category_ID);
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
     * Property of the variable category_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link IntegerProperty} - return the property of the variable category_ID.
     */
    public IntegerProperty category_IDProperty() {
        return category_ID;
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
