package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class Document_Language {

    private final StringProperty document_ID = new SimpleStringProperty("n/a");
    private final StringProperty language_ID = new SimpleStringProperty("n/a");
    private final StringProperty title = new SimpleStringProperty("n/a");
    private final StringProperty subTitle = new SimpleStringProperty("n/a");
    private final StringProperty subject = new SimpleStringProperty("n/a");
    private final StringProperty description = new SimpleStringProperty("n/a");
    private final StringProperty resume = new SimpleStringProperty("n/a");
    private final StringProperty notes = new SimpleStringProperty("n/a");

    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public Document_Language() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link Document_Language}
     */
    public Document_Language(Document_Language clone) {

        if (clone != null) {

            this.document_ID.set(clone.document_ID.get());

            this.language_ID.set(clone.language_ID.get());
            this.title.set(clone.title.get());
            this.subTitle.set(clone.subTitle.get());
            this.subject.set(clone.subject.get());
            this.description.set(clone.description.get());
            this.resume.set(clone.resume.get());
            this.notes.set(clone.notes.get());
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
     * Getter for the variable language_ID.
     *
     * @return {@link String} - return the variable language_ID.
     */
    public String getlanguage_ID() {
        return language_ID.get();
    }

    /**
     * Getter for the variable title.
     *
     * @return String - return the variable title.
     */
    public String gettitle() {
        return title.get();
    }

    /**
     * Getter for the variable subTitle.
     *
     * @return {@link String} - return the variable subTitle.
     */
    public String getsubTitle() {
        return subTitle.get();
    }

    /**
     * Getter for the variable subject.
     *
     * @return {@link String} - return the variable subject.
     */
    public String getsubject() {
        return subject.get();
    }

    /**
     * Getter for the variable description.
     *
     * @return {@link String} - return the variable description.
     */
    public String getdescription() {
        return description.get();
    }

    /**
     * Getter for the variable description.
     *
     * @return {@link String} - return the variable description.
     */
    public String getresume() {
        return resume.get();
    }

    /**
     * Getter for the variable notes.
     *
     * @return {@link String} - return the variable notes.
     */
    public String getnotes() {
        return notes.get();
    }

    /**
     * Setter for the variable document_ID.
     *
     * @param document_ID - {@link String} - index of the Document_Language class.
     * @return - {@link Document_Language} - builder pattern return
     */
    public Document_Language setdocument_ID(String document_ID) {
        this.document_ID.set(document_ID);
        return this;
    }

    /**
     * Setter for the variable language_ID.
     *
     * @param language_ID - {@link String} - language_ID of this class.
     * @return - {@link Document_Language} - builder pattern return
     */
    public Document_Language setlanguage_ID(String language_ID) {
        this.language_ID.set(language_ID);
        return this;
    }

    /**
     * Setter for the variable title.
     *
     * @param title - {@link String} - title of this class.
     * @return - {@link Document_Language} - builder pattern return
     */
    public Document_Language settitle(String title) {
        this.title.set(title);
        return this;
    }

    /**
     * Setter for the variable subTitle.
     *
     * @param subTitle - {@link String} - subTitle of this class.
     * @return - {@link Document_Language} - builder pattern return
     */
    public Document_Language setsubTitle(String subTitle) {
        this.subTitle.set(subTitle);
        return this;
    }

    /**
     * Setter for the variable subject.
     *
     * @param subject - {@link String} - subject of this class.
     * @return - {@link Document_Language} - builder pattern return
     */
    public Document_Language setsubject(String subject) {
        this.subject.set(subject);
        return this;
    }

    /**
     * Setter for the variable description.
     *
     * @param description - {@link String} - description of this class.
     * @return - {@link Document_Language} - builder pattern return
     */
    public Document_Language setdescription(String description) {
        this.description.set(description);
        return this;
    }

    /**
     * Setter for the variable resume.
     *
     * @param resume - {@link String} - resume of this class.
     * @return - {@link Document_Language} - builder pattern return
     */
    public Document_Language setresume(String resume) {
        this.resume.set(resume);
        return this;
    }

    /**
     * Setter for the variable notes.
     *
     * @param notes - {@link String} - notes of this class.
     * @return - {@link Document_Language} - builder pattern return
     */
    public Document_Language setnotes(String notes) {
        this.notes.set(notes);
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
     * Property of the variable title.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable title.
     */
    public StringProperty titleProperty() {
        return title;
    }

    /**
     * Property of the variable subTitle.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable subTitle.
     */
    public StringProperty subTitleProperty() {
        return subTitle;
    }

    /**
     * Property of the variable subject.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable subject.
     */
    public StringProperty subjectProperty() {
        return subject;
    }

    /**
     * Property of the variable description.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable description.
     */
    public StringProperty descriptionProperty() {
        return description;
    }

    /**
     * Property of the variable resume.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable resume.
     */
    public StringProperty resumeProperty() {
        return resume;
    }

    /**
     * Property of the variable notes.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable notes.
     */
    public StringProperty notesProperty() {
        return notes;
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
