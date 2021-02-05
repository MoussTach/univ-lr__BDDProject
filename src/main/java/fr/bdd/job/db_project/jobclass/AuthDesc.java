package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;

import java.util.Date;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class AuthDesc {

    private final ObjectProperty<Document> document_ID = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Person> person_ID = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Language> language_ID = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Date> dateNotice = new SimpleObjectProperty<>(null);

    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public AuthDesc() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link AuthDesc}
     */
    public AuthDesc(AuthDesc clone) {

        if (clone != null) {

            this.document_ID.set(clone.document_ID.get());

            this.person_ID.set(clone.person_ID.get());
            this.language_ID.set(clone.language_ID.get());
            this.dateNotice.set(clone.dateNotice.get());
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
     * Getter for the variable language.
     *
     * @return {@link Language} - return the variable language.
     */
    public Language getlanguage_ID() {
        return language_ID.get();
    }

    /**
     * Getter for the variable dateNotice.
     *
     * @return {@link Date} - return the variable dateNotice.
     */
    public Date getdateNotice() {
        return dateNotice.get();
    }

    /**
     * Setter for the variable document_ID.
     *
     * @param document_ID - {@link Document} - index of the AuthDesc class.
     * @return - {@link AuthDesc} - builder pattern return
     */
    public AuthDesc setdocument_ID(Document document_ID) {
        this.document_ID.set(document_ID);
        return this;
    }

    /**
     * Setter for the variable person_ID.
     *
     * @param person_ID - {@link Person} - person_ID of this class.
     * @return - {@link AuthDesc} - builder pattern return
     */
    public AuthDesc setperson_ID(Person person_ID) {
        this.person_ID.set(person_ID);
        return this;
    }

    /**
     * Setter for the variable language.
     *
     * @param language - {@link Language} - language of this class.
     * @return - {@link AuthDesc} - builder pattern return
     */
    public AuthDesc setlanguage_ID(Language language) {
        this.language_ID.set(language);
        return this;
    }

    /**
     * Setter for the variable dateNotice.
     *
     * @param dateNotice - {@link Date} - dateNotice of this class.
     * @return - {@link AuthDesc} - builder pattern return
     */
    public AuthDesc setdateNotice(Date dateNotice) {
        this.dateNotice.set(dateNotice);
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
     * Property of the variable language.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty} - return the property of the variable language.
     */
    public ObjectProperty<Language> languageProperty() {
        return language_ID;
    }

    /**
     * Property of the variable dateNotice.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty} - return the property of the variable dateNotice.
     */
    public ObjectProperty<Date> dateNoticeProperty() {
        return dateNotice;
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
