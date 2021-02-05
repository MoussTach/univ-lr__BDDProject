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

    private final ObjectProperty<Document> document_ID = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Author> author_ID = new SimpleObjectProperty<>(null);

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

            this.author_ID.set(clone.author_ID.get());
        }
    }

    /**
     * Getter for the variable document_ID.
     *
     * @return {@link Document} - return the variable document_ID.
     */
    public Document getdocument_ID() {
        return document_ID.get();
    }

    /**
     * Getter for the variable author_ID.
     *
     * @return {@link Author} - return the variable author_ID.
     */
    public Author getauthor_ID() {
        return author_ID.get();
    }

    /**
     * Setter for the variable document_ID.
     *
     * @param document_ID - {@link Document} - index of the Document_Author class.
     * @return - {@link Document_Author} - builder pattern return
     */
    public Document_Author setdocument_ID(Document document_ID) {
        this.document_ID.set(document_ID);
        return this;
    }

    /**
     * Setter for the variable author_ID.
     *
     * @param author_ID - {@link Author} - author_ID of this class.
     * @return - {@link Document_Author} - builder pattern return
     */
    public Document_Author setauthor_ID(Author author_ID) {
        this.author_ID.set(author_ID);
        return this;
    }

    /**
     * Property of the variable document_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty} - return the property of the variable document_ID.
     */
    public ObjectProperty<Document> document_IDProperty() {
        return document_ID;
    }

    /**
     * Property of the variable author_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty} - return the property of the variable author_ID.
     */
    public ObjectProperty<Author> author_IDProperty() {
        return author_ID;
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
