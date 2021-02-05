package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;

import java.util.Date;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class AuthRevision {

    private final ObjectProperty<Document> document_ID = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Person> person_ID = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Date> date = new SimpleObjectProperty<>(null);

    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public AuthRevision() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link AuthRevision}
     */
    public AuthRevision(AuthRevision clone) {

        if (clone != null) {

            this.document_ID.set(clone.document_ID.get());

            this.person_ID.set(clone.person_ID.get());
            this.date.set(clone.date.get());
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
     * Getter for the variable person_ID.
     *
     * @return {@link Person} - return the variable person_ID.
     */
    public Person getperson_ID() {
        return person_ID.get();
    }

    /**
     * Getter for the variable date.
     *
     * @return {@link Date} - return the variable date.
     */
    public Date getdate() {
        return date.get();
    }

    /**
     * Setter for the variable document_ID.
     *
     * @param document_ID - {@link Document} - index of the AuthRevision class.
     * @return - {@link AuthRevision} - builder pattern return
     */
    public AuthRevision setdocument_ID(Document document_ID) {
        this.document_ID.set(document_ID);
        return this;
    }

    /**
     * Setter for the variable person_ID.
     *
     * @param person_ID - {@link Person} - person_ID of this class.
     * @return - {@link AuthRevision} - builder pattern return
     */
    public AuthRevision setperson_ID(Person person_ID) {
        this.person_ID.set(person_ID);
        return this;
    }

    /**
     * Setter for the variable date.
     *
     * @param date - {@link Date} - date of this class.
     * @return - {@link AuthRevision} - builder pattern return
     */
    public AuthRevision setdate(Date date) {
        this.date.set(date);
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
     * Property of the variable person_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty} - return the property of the variable person_ID.
     */
    public ObjectProperty<Person> person_IDProperty() {
        return person_ID;
    }

    /**
     * Property of the variable date.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty} - return the property of the variable date.
     */
    public ObjectProperty<Date> dateProperty() {
        return date;
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
