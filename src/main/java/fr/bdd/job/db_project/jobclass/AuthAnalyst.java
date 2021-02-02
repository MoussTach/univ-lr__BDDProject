package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;

import java.util.Date;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class AuthAnalyst {

    private final StringProperty authAnalyst_ID = new SimpleStringProperty("n/a");
    private final ObjectProperty<Person> person_ID = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Date> date = new SimpleObjectProperty<>(null);

    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public AuthAnalyst() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link AuthAnalyst}
     */
    public AuthAnalyst(AuthAnalyst clone) {

        if (clone != null) {

            this.authAnalyst_ID.set(clone.authAnalyst_ID.get());

            this.person_ID.set(clone.person_ID.get());
            this.date.set(clone.date.get());
        }
    }

    /**
     * Getter for the variable authAnalyst_ID.
     *
     * @return {@link String} - return the variable authAnalyst_ID.
     */
    public String getauthAnalyst_ID() {
        return authAnalyst_ID.get();
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
     * Setter for the variable authAnalyst_ID.
     *
     * @param authAnalyst_ID - {@link String} - index of the AuthAnalyst class.
     * @return - {@link AuthAnalyst} - builder pattern return
     */
    public AuthAnalyst setauthAnalyst_ID(String authAnalyst_ID) {
        this.authAnalyst_ID.set(authAnalyst_ID);
        return this;
    }

    /**
     * Setter for the variable person_ID.
     *
     * @param person_ID - {@link Person} - person_ID of this class.
     * @return - {@link AuthAnalyst} - builder pattern return
     */
    public AuthAnalyst setperson_ID(Person person_ID) {
        this.person_ID.set(person_ID);
        return this;
    }

    /**
     * Setter for the variable date.
     *
     * @param date - {@link Date} - date of this class.
     * @return - {@link AuthAnalyst} - builder pattern return
     */
    public AuthAnalyst setdate(Date date) {
        this.date.set(date);
        return this;
    }

    /**
     * Property of the variable authAnalyst_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable authAnalyst_ID.
     */
    public StringProperty authAnalyst_IDProperty() {
        return authAnalyst_ID;
    }

    /**
     * Property of the variable person_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<Person>} - return the property of the variable person_ID.
     */
    public ObjectProperty<Person> person_IDProperty() {
        return person_ID;
    }

    /**
     * Property of the variable date.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<Date>} - return the property of the variable date.
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
        return String.format("%s", authAnalyst_ID.get());
    }
}
