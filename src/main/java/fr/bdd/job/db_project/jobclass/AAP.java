package fr.bdd.job.db_project.jobclass;

import javafx.beans.property.*;

import java.util.Date;

/**
 * Job class.
 * Client on the database (getted with the Sage database throught a ODBC).
 *
 * @author Gaetan Brenckle
 */
public class AAP {

    private final IntegerProperty AAP_ID = new SimpleIntegerProperty(0);
    private final ObjectProperty<Person> responsable = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Person> student = new SimpleObjectProperty<>(null);
    private final StringProperty edition = new SimpleStringProperty("n/a");

    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public AAP() {}

    /**
     * Clone constructor
     *
     * @param clone - {@link AAP}
     */
    public AAP(AAP clone) {

        if (clone != null) {
            this.AAP_ID.set(clone.AAP_ID.get());
            this.responsable.set(clone.responsable.get());
            this.student.set(clone.student.get());
            this.edition.set(clone.edition.get());
        }
    }

    /**
     * Getter for the variable AAP_ID.
     *
     * @return {@link Integer} - return the variable AAP_ID.
     */
    public Integer getAAP_ID() {
        return AAP_ID.get();
    }

    /**
     * Getter for the variable responsable.
     *
     * @return {@link Person} - return the variable responsable.
     */
    public Person getresponsable() {
        return responsable.get();
    }

    /**
     * Getter for the variable student.
     *
     * @return {@link Person} - return the variable student.
     */
    public Person getstudent() {
        return student.get();
    }

    /**
     * Getter for the variable edition.
     *
     * @return String - return the variable edition.
     */
    public String getedition() {
        return edition.get();
    }

    /**
     * Setter for the variable AAP_ID.
     *
     * @param AAP_ID - {@link Person} - index of the AAP class.
     * @return - {@link AAP} - builder pattern return
     */
    public AAP setAAP_ID(Integer AAP_ID) {
        this.AAP_ID.set(AAP_ID);
        return this;
    }

    /**
     * Setter for the variable responsable.
     *
     * @param responsable - {@link Person} - responsable of this class.
     * @return - {@link AAP} - builder pattern return
     */
    public AAP setresponsable(Person responsable) {
        this.responsable.set(responsable);
        return this;
    }

    /**
     * Setter for the variable student.
     *
     * @param student - {@link Person} - student of this class.
     * @return - {@link AAP} - builder pattern return
     */
    public AAP setstudent(Person student) {
        this.student.set(student);
        return this;
    }

    /**
     * Setter for the variable edition.
     *
     * @param edition - {@link String} - edition of this class.
     * @return - {@link AAP} - builder pattern return
     */
    public AAP setedition(String edition) {
        this.edition.set(edition);
        return this;
    }

    /**
     * Property of the variable AAP_ID.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link IntegerProperty} - return the property of the variable AAP_ID.
     */
    public IntegerProperty AAP_IDProperty() {
        return AAP_ID;
    }

    /**
     * Property of the variable responsable.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<Person>} - return the property of the variable responsable.
     */
    public ObjectProperty<Person> responsableProperty() {
        return responsable;
    }

    /**
     * Property of the variable student.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty<Person>} - return the property of the variable student.
     */
    public ObjectProperty<Person> studentProperty() {
        return student;
    }

    /**
     * Property of the variable edition.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable edition.
     */
    public StringProperty editionProperty() {
        return edition;
    }

    /**
     * ToString methods, created when this class is used on listing
     *
     * @return - {@link String} - the builded list
     */
    @Override
    public String toString() {
        return String.format("%s", AAP_ID.get());
    }
}
