package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class Project {

    private final StringProperty project_ID = new SimpleStringProperty("n/a");
    
    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public Project() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link Project}
     */
    public Project(Project clone) {

        if (clone != null) {

            this.project_ID.set(clone.project_ID.get());
        }
    }

    /**
     * Getter for the variable project_ID.
     *
     * @return {@link String} - return the variable project_ID.
     */
    public String getproject_ID() {
        return project_ID.get();
    }

    /**
     * Setter for the variable project_ID.
     *
     * @param project_ID - {@link String} - index of the Project class.
     * @return - {@link Project} - builder pattern return
     */
    public Project setproject_ID(String project_ID) {
        this.project_ID.set(project_ID);
        return this;
    }

    /**
     * Property of the variable project_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable project_ID.
     */
    public StringProperty project_IDProperty() {
        return project_ID;
    }

    /**
     * ToString methods, created when this class is used on listing
     *
     * @return - {@link String} - the builded list
     */
    @Override
    public String toString() {
        return String.format("%s", project_ID.get());
    }
}
