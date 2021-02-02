package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class RespGroup {

    private final StringProperty respGroup_ID = new SimpleStringProperty("n/a");
    
    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public RespGroup() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link RespGroup}
     */
    public RespGroup(RespGroup clone) {

        if (clone != null) {

            this.respGroup_ID.set(clone.respGroup_ID.get());
        }
    }

    /**
     * Getter for the variable respGroup_ID.
     *
     * @return {@link String} - return the variable respGroup_ID.
     */
    public String getrespGroup_ID() {
        return respGroup_ID.get();
    }

    /**
     * Setter for the variable respGroup_ID.
     *
     * @param respGroup_ID - {@link String} - index of the RespGroup class.
     * @return - {@link RespGroup} - builder pattern return
     */
    public RespGroup setrespGroup_ID(String respGroup_ID) {
        this.respGroup_ID.set(respGroup_ID);
        return this;
    }

    /**
     * Property of the variable respGroup_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable respGroup_ID.
     */
    public StringProperty respGroup_IDProperty() {
        return respGroup_ID;
    }

    /**
     * ToString methods, created when this class is used on listing
     *
     * @return - {@link String} - the builded list
     */
    @Override
    public String toString() {
        return String.format("%s", respGroup_ID.get());
    }
}
