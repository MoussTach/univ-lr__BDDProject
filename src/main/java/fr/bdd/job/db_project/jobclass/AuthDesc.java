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

    private final StringProperty authDesc_ID = new SimpleStringProperty("n/a");
    private final IntegerProperty person_ID = new SimpleIntegerProperty(0);
    private final StringProperty language = new SimpleStringProperty("n/a");
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

            this.authDesc_ID.set(clone.authDesc_ID.get());

            this.person_ID.set(clone.person_ID.get());
            this.language.set(clone.language.get());
            this.dateNotice.set(clone.dateNotice.get());
        }
    }

    /**
     * Getter for the variable authDesc_ID.
     *
     * @return {@link String} - return the variable authDesc_ID.
     */
    public String getauthDesc_ID() {
        return authDesc_ID.get();
    }

    /**
     * Getter for the variable person_ID.
     *
     * @return {@link Integer} - return the variable person_ID.
     */
    public Integer getperson_ID() {
        return person_ID.get();
    }

    /**
     * Getter for the variable language.
     *
     * @return {@link String} - return the variable language.
     */
    public String getlanguage() {
        return language.get();
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
     * Setter for the variable authDesc_ID.
     *
     * @param authDesc_ID - {@link String} - index of the AuthDesc class.
     * @return - {@link AuthDesc} - builder pattern return
     */
    public AuthDesc setauthDesc_ID(String authDesc_ID) {
        this.authDesc_ID.set(authDesc_ID);
        return this;
    }

    /**
     * Setter for the variable person_ID.
     *
     * @param person_ID - {@link Integer} - person_ID of this class.
     * @return - {@link AuthDesc} - builder pattern return
     */
    public AuthDesc setperson_ID(Integer person_ID) {
        this.person_ID.set(person_ID);
        return this;
    }

    /**
     * Setter for the variable language.
     *
     * @param language - {@link String} - language of this class.
     * @return - {@link AuthDesc} - builder pattern return
     */
    public AuthDesc setlanguage(String language) {
        this.language.set(language);
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
     * Property of the variable authDesc_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable authDesc_ID.
     */
    public StringProperty authDesc_IDProperty() {
        return authDesc_ID;
    }

    /**
     * Property of the variable person_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link IntegerProperty} - return the property of the variable person_ID.
     */
    public IntegerProperty person_IDProperty() {
        return person_ID;
    }

    /**
     * Property of the variable language.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable language.
     */
    public StringProperty languageProperty() {
        return language;
    }

    /**
     * Property of the variable dateNotice.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<Date>} - return the property of the variable dateNotice.
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
        return String.format("%s", authDesc_ID.get());
    }
}
