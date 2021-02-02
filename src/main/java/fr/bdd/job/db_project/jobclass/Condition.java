package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;


/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class Condition {

    private final StringProperty condition_ID = new SimpleStringProperty("n/a");

    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public Condition() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link Condition}
     */
    public Condition(Condition clone) {

        if (clone != null) {

            this.condition_ID.set(clone.condition_ID.get());
        }
    }

    /**
     * Getter for the variable condition_ID.
     *
     * @return {@link String} - return the variable condition_ID.
     */
    public String getcondition_ID() {
        return condition_ID.get();
    }

    /**
     * Setter for the variable condition_ID.
     *
     * @param condition_ID - {@link String} - index of the Condition class.
     * @return - {@link Condition} - builder pattern return
     */
    public Condition setcondition_ID(String condition_ID) {
        this.condition_ID.set(condition_ID);
        return this;
    }

    /**
     * Property of the variable condition_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable condition_ID.
     */
    public StringProperty condition_IDProperty() {
        return condition_ID;
    }

    /**
     * ToString methods, created when this class is used on listing
     *
     * @return - {@link String} - the builded list
     */
    @Override
    public String toString() {
        return String.format("%s", condition_ID.get());
    }
}
