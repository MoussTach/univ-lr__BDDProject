package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class NatureOfDoc {

    private final StringProperty natureOfDoc_ID = new SimpleStringProperty("n/a");
    private final StringProperty supportOfDoc = new SimpleStringProperty("n/a");

    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public NatureOfDoc() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link NatureOfDoc}
     */
    public NatureOfDoc(NatureOfDoc clone) {

        if (clone != null) {

            this.natureOfDoc_ID.set(clone.natureOfDoc_ID.get());

            this.supportOfDoc.set(clone.supportOfDoc.get());
        }
    }

    /**
     * Getter for the variable natureOfDoc_ID.
     *
     * @return {@link String} - return the variable natureOfDoc_ID.
     */
    public String getnatureOfDoc_ID() {
        return natureOfDoc_ID.get();
    }

    /**
     * Getter for the variable supportOfDoc.
     *
     * @return {@link String} - return the variable supportOfDoc.
     */
    public String getsupportOfDoc() {
        return supportOfDoc.get();
    }

    /**
     * Setter for the variable natureOfDoc_ID.
     *
     * @param natureOfDoc_ID - {@link String} - index of the NatureOfDoc class.
     * @return - {@link NatureOfDoc} - builder pattern return
     */
    public NatureOfDoc setnatureOfDoc_ID(String natureOfDoc_ID) {
        this.natureOfDoc_ID.set(natureOfDoc_ID);
        return this;
    }

    /**
     * Setter for the variable supportOfDoc.
     *
     * @param supportOfDoc - {@link String} - supportOfDoc of this class.
     * @return - {@link NatureOfDoc} - builder pattern return
     */
    public NatureOfDoc setsupportOfDoc(String supportOfDoc) {
        this.supportOfDoc.set(supportOfDoc);
        return this;
    }

    /**
     * Property of the variable natureOfDoc_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable natureOfDoc_ID.
     */
    public StringProperty natureOfDoc_IDProperty() {
        return natureOfDoc_ID;
    }

    /**
     * Property of the variable supportOfDoc.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable supportOfDoc.
     */
    public StringProperty supportOfDocProperty() {
        return supportOfDoc;
    }

    /**
     * ToString methods, created when this class is used on listing
     *
     * @return - {@link String} - the builded list
     */
    @Override
    public String toString() {
        return String.format("%s", natureOfDoc_ID.get());
    }
}
