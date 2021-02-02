package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class Person {

    private final IntegerProperty person_ID = new SimpleIntegerProperty(0);
    private final StringProperty name = new SimpleStringProperty("n/a");
    private final StringProperty title = new SimpleStringProperty("n/a");
    
    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public Person() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link Person}
     */
    public Person(Person clone) {

        if (clone != null) {

            this.person_ID.set(clone.person_ID.get());

            this.name.set(clone.name.get());
            this.title.set(clone.title.get());
        }
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
     * Getter for the variable name.
     *
     * @return {@link String} - return the variable name.
     */
    public String getname() {
        return name.get();
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
     * Setter for the variable person_ID.
     *
     * @param person_ID - {@link Integer} - index of the Person class.
     * @return - {@link Person} - builder pattern return
     */
    public Person setperson_ID(Integer person_ID) {
        this.person_ID.set(person_ID);
        return this;
    }

    /**
     * Setter for the variable name.
     *
     * @param name - {@link String} - name of this class.
     * @return - {@link Person} - builder pattern return
     */
    public Person setname(String name) {
        this.name.set(name);
        return this;
    }

    /**
     * Setter for the variable title.
     *
     * @param title - {@link String} - title of this class.
     * @return - {@link Person} - builder pattern return
     */
    public Person settitle(String title) {
        this.title.set(title);
        return this;
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
     * Property of the variable name.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable name.
     */
    public StringProperty nameProperty() {
        return name;
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
     * ToString methods, created when this class is used on listing
     *
     * @return - {@link String} - the builded list
     */
    @Override
    public String toString() {
        return String.format("%s", person_ID.get());
    }
}
